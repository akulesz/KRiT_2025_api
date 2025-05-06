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
                .author(report.getAuthor())
                .eventId(report.event.getId())
                .build();
    }

    @Transactional
    public Report createReport(ReportCreateDTO reportCreateDTO) {
        // Znajdź wydarzenie przed utworzeniem raportu
        Event event = eventRepository.findById(reportCreateDTO.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event with ID " + reportCreateDTO.getEventId() + " not found"));

        // Utwórz i skonfiguruj raport
        Report report = new Report();
        report.setTitle(reportCreateDTO.getTitle());
        report.setAuthor(reportCreateDTO.getAuthor());
        report.setDescription(reportCreateDTO.getDescription());
        report.setPdfURL(reportCreateDTO.getPdfURL());
        report.setKeywords(reportCreateDTO.getKeywords());

        // Ustaw relację jednostronną (tylko raport wie o wydarzeniu)
        report.setEvent(event);

        // Zapisz raport
        Report savedReport = reportRepository.save(report);
        System.out.println(savedReport.title);
        System.out.println(event.getTitle());

        // Opcjonalnie, jeśli rzeczywiście potrzebujesz dwukierunkowej relacji:
        // event.getReports().add(savedReport);
        // eventRepository.save(event); // To może nie być konieczne z odpowiednią konfiguracją cascade

        return savedReport;
    }


    @Transactional
    public void deleteReportById(UUID id) {
        if(!reportRepository.existsById(id)) {
            throw new EntityNotFoundException("Brak raportu o id: "+id);
        }
        reportRepository.deleteById(id);
    }



}

