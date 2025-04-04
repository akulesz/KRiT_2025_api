package com.example.KRiT_2025_backend.Event;

import com.example.KRiT_2025_backend.Event.EventDTOs.EventCreateDTO;
import com.example.KRiT_2025_backend.Event.EventDTOs.EventListDTO;
import com.example.KRiT_2025_backend.Event.EventDTOs.EventReadDTO;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportListDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<EventListDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(event->
                EventListDTO.builder()
                        .title(event.getTitle())
                        .id(event.getId())
                        .build()
        ).collect(Collectors.toList());

    };

    public EventReadDTO findEventById(UUID id) {
        System.out.println("Szukane ID: " + id);
        Event event = eventRepository.findById(id)
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
        event.description = eventDTO.getDescription();
        event.building=eventDTO.getBuilding();
        event.room=eventDTO.getRoom();

        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEventById(UUID id) {
        if (!eventRepository.existsById(id)) {
            throw new EntityNotFoundException("Brak eventu o id: " + id);
        }
        eventRepository.deleteById(id);
    }


    //NIE TESTOWANE CZEKAM NA FRONTEND
    @Transactional
    public EventReadDTO updateEvent(UUID id, EventCreateDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brak eventu o id: " + id));

        event.setTitle(eventDTO.getTitle());
        event.setSubtitle(eventDTO.getSubtitle());
        event.setType(eventDTO.getType());
        event.setDateTimeStart(eventDTO.getDateTimeStart());
        event.setDateTimeEnd(eventDTO.getDateTimeEnd());
        event.setDescription(eventDTO.getDescription());
        event.setBuilding(eventDTO.getBuilding());
        event.setRoom(eventDTO.getRoom());

        eventRepository.save(event);
        return mapEventToEventReadDto(event);
    }



    public EventReadDTO mapEventToEventReadDto(Event event) {
        return EventReadDTO.builder()
                .title(event.getTitle())
                .id(event.getId())

                .build();
    }
}
