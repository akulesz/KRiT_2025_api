package com.example.KRiT_2025_backend.Report;

import com.example.KRiT_2025_backend.Event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    Event event;

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", pdfURL='" + pdfURL + '\'' +
                ", keywords=" + keywords +
                // 🔴 Nie dodawaj eventu!
                '}';
}}
