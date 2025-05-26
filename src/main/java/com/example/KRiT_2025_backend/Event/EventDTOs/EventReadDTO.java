package com.example.KRiT_2025_backend.Event.EventDTOs;

import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportListDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class EventReadDTO {
    UUID id;
    String title;
    public List<ReportListDTO> reports;

    public EventReadDTO(UUID id, String title, List<ReportListDTO> reports) {
        this.id = id;
        this.title = title;
        this.reports = reports;
    }
}
