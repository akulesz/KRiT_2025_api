package com.example.KRiT_2025_backend.Report;

import com.example.KRiT_2025_backend.Event.Event;
import com.example.KRiT_2025_backend.Event.EventDTOs.EventCreateDTO;
import com.example.KRiT_2025_backend.Event.EventRepository;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportCreateDTO;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportListDTO;
import com.example.KRiT_2025_backend.Report.ReportDTOs.ReportReadDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final EventRepository eventRepository;

    public ReportService(ReportRepository reportRepository, EventRepository eventRepository) {
        this.reportRepository = reportRepository;
        this.eventRepository = eventRepository;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll().stream().collect(Collectors.toList());
    }

    public Report getReportById(UUID id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Nie znaleziono reportu o id " + id ));

        return report;
    }


    public ReportReadDTO mapReportToReportReadDTO(Report report) {
        return ReportReadDTO.builder()
                .id(report.getId())
                .title(report.getTitle())
                .authors(report.getAuthors())
                .eventId(report.event.getId())
                .build();
    }

    @Transactional
    public Report createReport(ReportCreateDTO reportCreateDTO) {

        Report report = new Report();
        report.setTitle(reportCreateDTO.getTitle());
        report.setAuthors(reportCreateDTO.getAuthors());
        report.setDescription(reportCreateDTO.getDescription());
        report.setPdfURL(reportCreateDTO.getPdfURL());
        report.setKeywords(reportCreateDTO.getKeywords());

        Report savedReport = reportRepository.save(report);
        System.out.println(savedReport.title);

        return savedReport;
    }

    @Transactional
    public Report updateReport(UUID id, ReportCreateDTO reportDTO) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brak raportu o id: " + id));

        report.setTitle(reportDTO.getTitle());
        report.setDescription(reportDTO.getDescription());
        report.setAuthors(reportDTO.getAuthors());
        report.setPdfURL(reportDTO.getPdfURL());
        report.setKeywords(reportDTO.getKeywords());

        reportRepository.save(report);
        return report;
    }



    @Transactional
    public void deleteReportById(UUID id) {
        if(!reportRepository.existsById(id)) {
            throw new EntityNotFoundException("Brak raportu o id: "+id);
        }
        reportRepository.deleteById(id);
    }



}

