package com.example.KRiT_2025_backend.Report;

import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportCreateDTO;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportListDTO;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportReadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<ReportListDTO>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportReadDTO> getReportById(@PathVariable UUID id) {
        ReportReadDTO reportReadDTO = reportService.getReportById(id);
        return ResponseEntity.ok(reportReadDTO);
    }

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody ReportCreateDTO reportCreateDTO) {
        Report createdReport = reportService.createReport(reportCreateDTO);
        return ResponseEntity.ok(createdReport);
    }

}
