package com.example.KRiT_2025_backend.Event;

import com.example.KRiT_2025_backend.Report.Report;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime dateTimeStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime dateTimeEnd;

    String building;
    String room;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    List<Report> reports = new ArrayList<>();
    boolean isFavourite;
}
