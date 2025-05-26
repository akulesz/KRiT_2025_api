package com.example.KRiT_2025_backend.Event.EventDTOs;

import com.example.KRiT_2025_backend.Event.EventType;
import com.example.KRiT_2025_backend.Report.Report;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportCreateDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventCreateDTO {
    private String title;
    private  String subtitle;

    private  EventType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private  LocalDateTime dateTimeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dateTimeEnd;

    //private String description;
    private  String building;
    private  String room;

    private List<UUID> reportsId;
}
