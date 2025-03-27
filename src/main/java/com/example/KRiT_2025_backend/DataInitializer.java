package com.example.KRiT_2025_backend;

import com.example.KRiT_2025_backend.Event.Event;
import com.example.KRiT_2025_backend.Event.EventType;
import com.example.KRiT_2025_backend.Report.Report;


import com.example.KRiT_2025_backend.Event.EventRepository;
import com.example.KRiT_2025_backend.Report.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ReportRepository reportRepository;

    @Override
    public void run(String... args) throws Exception {

        Event event = new Event();
        event.setTitle("Konferencja IT");
        event.setSubtitle("Nowe technologie w IT");
        event.setType(EventType.PlenarySession);
        event.setDateTimeStart(LocalDateTime.now());
        event.setDateTimeEnd(LocalDateTime.now().plusHours(2));
        event.setDescription("Konferencja poświęcona nowinkom technologicznym");
        event.setBuilding("Budynek A");
        event.setRoom("Sala 101");
        event.setFavourite(false);

        eventRepository.save(event);

        System.out.println("Zapisano event o ID: " + event.getId());

        Report report = new Report();
        report.setTitle("Raport 1");
        report.setAuthor("Jan Kowalski");
        report.setDescription("Raport o nowych trendach w IT");
        report.setPdfURL("https://example.com/raport.pdf");
        // Dodanie słów kluczowych
        report.setKeywords(List.of("nowe technologie", "IT", "trendy", "programowanie"));
        report.setEvent(event);
        //event.getReports().add(report);
        reportRepository.save(report);

        System.out.print("\n\nID EVENTU "+ event.getId()+"\n\n");
    }
}
