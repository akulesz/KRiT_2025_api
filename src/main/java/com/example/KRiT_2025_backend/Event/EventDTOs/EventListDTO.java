package com.example.KRiT_2025_backend.Event.EventDTOs;

import com.example.KRiT_2025_backend.Event.EventType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class EventListDTO {
    String title;
}
