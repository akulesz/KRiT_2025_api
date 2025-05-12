package com.example.KRiT_2025_backend.Event;

import com.example.KRiT_2025_backend.Event.EventDTOs.EventCreateDTO;
import com.example.KRiT_2025_backend.Event.EventDTOs.EventListDTO;
import com.example.KRiT_2025_backend.Event.EventDTOs.EventReadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController

@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventReadDTO> getEventById(@PathVariable UUID id) {
        EventReadDTO event = eventService.findEventById(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventCreateDTO eventDTO) {
        Event createdEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.ok(createdEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable UUID id) {
        eventService.deleteEventById(id);
        return ResponseEntity.ok("✅ Event o ID " + id + " został poprawnie usunięty."); //204 No Content po usunięciu
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID id, @RequestBody EventCreateDTO eventDTO) {
        Event updatedEvent = eventService.updateEvent(id, eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }

}
