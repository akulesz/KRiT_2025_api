package com.example.KRiT_2025_backend.Event;

import com.example.KRiT_2025_backend.Report.Report;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String title;
    String subtitle;
    @Enumerated(EnumType.STRING)
    EventType type;
    LocalDateTime dateTimeStart;
    LocalDateTime dateTimeEnd;
    String description;
    String building;
    String room;

    //@OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    @OneToMany(mappedBy="event", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Report> reports;
    boolean isFavourite;



}
