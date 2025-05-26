package com.example.KRiT_2025_backend.Event.EventDTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class EventListDTO {
    UUID id;
    String title;
}
