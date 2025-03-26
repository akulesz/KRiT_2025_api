package com.example.KRiT_2025_backend.Event.EventDTOs;

import com.example.KRiT_2025_backend.Event.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class EventCreateDTO {
    String title;
    String subtitle;

    EventType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    LocalDateTime dateTimeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    LocalDateTime dateTimeEnd;
    String description;
    String building;
    String room;
}
