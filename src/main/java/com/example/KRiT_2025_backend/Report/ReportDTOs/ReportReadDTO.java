package com.example.KRiT_2025_backend.Report.ReportDTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class ReportReadDTO {
    private UUID id;
    private String title;
    private String author;
    private UUID eventId;
}
