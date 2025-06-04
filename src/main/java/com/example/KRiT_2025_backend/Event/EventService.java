package com.example.KRiT_2025_backend.Event;

import com.example.KRiT_2025_backend.Event.EventDTOs.EventCreateDTO;
import com.example.KRiT_2025_backend.Event.EventDTOs.EventListDTO;
import com.example.KRiT_2025_backend.Event.EventDTOs.EventReadDTO;
import com.example.KRiT_2025_backend.Report.Report;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportCreateDTO;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportListDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.KRiT_2025_backend.Report.ReportRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    private ReportRepository reportRepository;

    public EventService(ReportRepository reportRepository, EventRepository eventRepository) {
        this.reportRepository = reportRepository;
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll().stream().collect(Collectors.toList());
    };

    public EventReadDTO findEventById(UUID id) {
        System.out.println("Szukane ID: " + id);

        // Pobieramy wszystkie eventy
        List<Event> events = eventRepository.findAll();

        // Szukamy eventu z pasującym ID
        Event event = events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Brak eventu o tym id"));

        // Mapowanie raportów na DTO
        List<ReportListDTO> reports = event.getReports().stream()
                .map(report -> new ReportListDTO(report.getId(), report.getTitle()))
                .toList();

        return new EventReadDTO(
                event.getId(),
                event.getTitle(),
                reports
        );
    }

    @Transactional
    public Event createEvent(EventCreateDTO eventDTO) {
        Event event = new Event();
        event.title = eventDTO.getTitle();
        event.subtitle = eventDTO.getSubtitle();
        event.type = eventDTO.getType();
        event.dateTimeStart = eventDTO.getDateTimeStart();
        event.dateTimeEnd = eventDTO.getDateTimeEnd();
        //event.description = eventDTO.getDescription();
        event.building=eventDTO.getBuilding();
        event.room=eventDTO.getRoom();
        //Event savedEvent = eventRepository.save(event);

        if(eventDTO.getReportsId() != null){
            for (UUID reportId : eventDTO.getReportsId()) {
                Report report = reportRepository.findById(reportId).
                        orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));
                report.setEvent(event);
                event.getReports().add(report);
            }
        }
        return eventRepository.save(event);
    }

//    @Transactional
//    public void deleteEventById(UUID id) {
//        if (!eventRepository.existsById(id)) {
//            throw new EntityNotFoundException("Brak eventu o id: " + id);
//        }
//
//        EventReadDTO event = findEventById(id);
//
//        if(event.getReports() != null){
//            for (ReportListDTO report : event.getReports()) {
//                Report rep = reportRepository.findById(report.getId()).
//                        orElseThrow(() -> new RuntimeException("Report not found with id: " + report.getId()));
//                rep.setEvent(null);
//
//            }
//            event.getReports().clear();
//        }
//
//        eventRepository.deleteById(id);
//    }

@Transactional
public void deleteEventById(UUID id) {
    if (!eventRepository.existsById(id)) {
        throw new EntityNotFoundException("Brak eventu o id: " + id);
    }

    // Masowa aktualizacja raportów, które miały ten event
    reportRepository.clearEventReference(id);

    // Teraz usuń event
    eventRepository.deleteById(id);
}

    @Transactional
    public Event updateEvent(UUID id, EventCreateDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brak eventu o id: " + id));

        event.setTitle(eventDTO.getTitle());
        event.setSubtitle(eventDTO.getSubtitle());
        event.setType(eventDTO.getType());
        event.setDateTimeStart(eventDTO.getDateTimeStart());
        event.setDateTimeEnd(eventDTO.getDateTimeEnd());
        //event.setDescription(eventDTO.getDescription());
        event.setBuilding(eventDTO.getBuilding());
        event.setRoom(eventDTO.getRoom());
        //event.setFavourite(eventDTO.isFavourite());

        //usuwamy liste reportow
        event.getReports().clear();
        if(eventDTO.getReportsId() != null){
            for (UUID reportId : eventDTO.getReportsId()) {
                Report report = reportRepository.findById(reportId).
                        orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));
                report.setEvent(event);
                event.getReports().add(report);
            }
        }

        eventRepository.save(event);
        return event;
    }



    public EventReadDTO mapEventToEventReadDto(Event event) {
        return EventReadDTO.builder()
                .title(event.getTitle())
                .id(event.getId())

                .build();
    }
}
