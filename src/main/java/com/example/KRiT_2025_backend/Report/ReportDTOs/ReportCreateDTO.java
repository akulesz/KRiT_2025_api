package com.example.KRiT_2025_backend.Report.ReportDTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class ReportCreateDTO {
    private String title;
    private List<String> authors;
    private String description;
    private String pdfURL;
    private List<String> keywords;
    //private UUID eventId;
}
