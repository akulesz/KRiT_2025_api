package com.example.KRiT_2025_backend.Event;

import com.example.KRiT_2025_backend.Event.EventDTOs.EventCreateDTO;
import com.example.KRiT_2025_backend.Event.EventDTOs.EventReadDTO;
import com.example.KRiT_2025_backend.Report.Report;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportListDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.KRiT_2025_backend.Report.ReportRepository;

import java.util.List;
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

        // get all events
        List<Event> events = eventRepository.findAll();

        // get events with matching id
        Event event = events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Brak eventu o tym id"));

        // map to dto
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
        event.building=eventDTO.getBuilding();
        event.room=eventDTO.getRoom();

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

@Transactional
public void deleteEventById(UUID id) {
    if (!eventRepository.existsById(id)) {
        throw new EntityNotFoundException("Brak eventu o id: " + id);
    }

    //update report
    reportRepository.clearEventReference(id);

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
        event.setBuilding(eventDTO.getBuilding());
        event.setRoom(eventDTO.getRoom());

        //delete reports
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
}
