package com.example.KRiT_2025_backend.Event.EventDTOs;

import com.example.KRiT_2025_backend.Event.EventType;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportListDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class EventReadDTO {
    UUID id;
    String title;
    private List<ReportListDTO> reports;

    public EventReadDTO(UUID id, String title, List<ReportListDTO> reports) {
        this.id = id;
        this.title = title;
        this.reports = reports;
    }


}
