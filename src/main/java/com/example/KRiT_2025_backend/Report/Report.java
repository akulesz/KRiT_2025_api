package com.example.KRiT_2025_backend.Report;

import com.example.KRiT_2025_backend.Event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String title;
    String author;
    String description;
    String pdfURL;

    // Przechowywanie słów kluczowych w osobnej tabeli
    @ElementCollection
    @CollectionTable(name = "report_keywords", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "keywords")
    List<String> keywords;

    @ManyToOne
    @JoinColumn(name="event_id")
    Event event;

}
