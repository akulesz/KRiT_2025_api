package com.example.KRiT_2025_backend.Report.ReportDTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class ReportListDTO {
    UUID id;
    private String title;

    public ReportListDTO(UUID id, String title) {
        this.id = id;
        this.title = title;
    }


}
