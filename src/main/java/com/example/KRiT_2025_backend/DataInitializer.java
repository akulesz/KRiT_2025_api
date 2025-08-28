package com.example.KRiT_2025_backend;

import com.example.KRiT_2025_backend.Auth.AppUser;
import com.example.KRiT_2025_backend.Auth.AppUserRepository;
import com.example.KRiT_2025_backend.Event.Event;
import com.example.KRiT_2025_backend.Event.EventType;
import com.example.KRiT_2025_backend.Report.Report;


import com.example.KRiT_2025_backend.Event.EventRepository;
import com.example.KRiT_2025_backend.Report.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DataInitializer odpalony");

        String adminUsername = "admin";
        String adminRawPassword = "admin123";

        if (appUserRepository.findByUsername(adminUsername).isEmpty()) {
            AppUser admin = new AppUser();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminRawPassword));
            admin.setRole("admin");
            appUserRepository.save(admin);
            System.out.println("Admin został dodany do bazy.");
        } else {
            System.out.println("Admin już istnieje w bazie.");
        }

        Event event1 = new Event();
        event1.setTitle("Referat plenarny nr 1");
        event1.setSubtitle("Fale milimetrowe i subterahercowe w systemach komórkowych: Planowane wykorzystanie, modelowanie propagacji i wpływ na architekturę sieci");
        event1.setType(EventType.PlenarySession);
        event1.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 9, 45));
        event1.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 10, 30));
        event1.setBuilding("Budynek A WETI");
        event1.setRoom("Sala CW-1");
        //event1.setFavourite(false);
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setTitle("Sesja techniczna nr 1");
        event2.setSubtitle("Sztuczna inteligencja w systemach radiowych – I");
        event2.setType(EventType.TechnicalSession);
        event2.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 11, 0));
        event2.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 12, 30));
        event2.setBuilding("Centrum Wykładowo-Konferencyjne");
        event2.setRoom("Sala CW-1");
        //event2.setFavourite(false);
        eventRepository.save(event2);

        Event event3 = new Event();
        event3.setTitle("Sesja techniczna nr 2");
        event3.setSubtitle("Bezpieczeństwo sieci i systemów teleinformatycznych");
        event3.setType(EventType.TechnicalSession);
        event3.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 11, 0));
        event3.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 12, 30));
        event3.setBuilding("Centrum Wykładowo-Konferencyjne");
        event3.setRoom("Sala 022");
        //event3.setFavourite(false);
        eventRepository.save(event3);

        Event event4 = new Event();
        event4.setTitle("Sesja techniczna nr 3");
        event4.setSubtitle("Systemy radionawigacyjne i radiolokalizacyjne – I");
        event4.setType(EventType.TechnicalSession);
        event4.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 11, 0));
        event4.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 12, 30));
        event4.setBuilding("Centrum Wykładowo-Konferencyjne");
        event4.setRoom("Sala 023");
        //event4.setFavourite(false);
        eventRepository.save(event4);

        Event event5 = new Event();
        event5.setTitle("Sesja techniczna nr 4");
        event5.setSubtitle("Aspekty sieci rdzeniowych 5G i 6G – I");
        event5.setType(EventType.TechnicalSession);
        event5.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 11, 0));
        event5.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 12, 30));
        event5.setBuilding("Centrum Wykładowo-Konferencyjne");
        event5.setRoom("Sala 027");
        //event5.setFavourite(false);
        eventRepository.save(event5);

        Event event6 = new Event();
        event6.setTitle("Referat plenarny nr 2");
        event6.setSubtitle("Krzysztof Walkowiak");
        event6.setType(EventType.PlenarySession);
        event6.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 13, 45));
        event6.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 14, 30));
        event6.setBuilding("Centrum Wykładowo-Konferencyjne");
        event6.setRoom("Sala CW-1");
        //event6.setFavourite(false);
        eventRepository.save(event6);

        Event event7 = new Event();
        event7.setTitle("Sesja firmowa");
        event7.setSubtitle("");
        event7.setType(EventType.Other);
        event7.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 14, 30));
        event7.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 15, 30));
        event7.setBuilding("Centrum Wykładowo-Konferencyjne");
        event7.setRoom("CW-1");
        //event7.setFavourite(false);
        eventRepository.save(event7);

        Event event8 = new Event();
        event8.setTitle("Sesja techniczna nr 5");
        event8.setSubtitle("Sztuczna inteligencja w systemach radiowych – II");
        event8.setType(EventType.TechnicalSession);
        event8.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 16, 0));
        event8.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 17, 30));
        event8.setBuilding("Centrum Wykładowo-Konferencyjne");
        event8.setRoom("Sala CW-1");
        //event8.setFavourite(false);
        eventRepository.save(event8);

        Event event9 = new Event();
        event9.setTitle("Sesja techniczna nr 6");
        event9.setSubtitle("Kryptografia i mechanizmy cyberbezpieczeństwa");
        event9.setType(EventType.TechnicalSession);
        event9.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 16, 0));
        event9.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 17, 30));
        event9.setBuilding("Centrum Wykładowo-Konferencyjne");
        event9.setRoom("Sala 022");
        //event9.setFavourite(false);
        eventRepository.save(event9);

        Event event10 = new Event();
        event10.setTitle("Sesja techniczna nr 7");
        event10.setSubtitle("Systemy radionawigacyjne i radiolokalizacyjne – II");
        event10.setType(EventType.TechnicalSession);
        event10.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 16, 0));
        event10.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 17, 30));
        event10.setBuilding("Centrum Wykładowo-Konferencyjne");
        event10.setRoom("Sala 023");
        //event10.setFavourite(false);
        eventRepository.save(event10);

        Event event11 = new Event();
        event11.setTitle("Sesja techniczna nr 8");
        event11.setSubtitle("Aspekty sieciowe Internetu Rzeczy");
        event11.setType(EventType.TechnicalSession);
        event11.setDateTimeStart(LocalDateTime.of(2024, 9, 11, 16, 0));
        event11.setDateTimeEnd(LocalDateTime.of(2024, 9, 11, 17, 30));
        event11.setBuilding("Centrum Wykładowo-Konferencyjne");
        event11.setRoom("Sala 027");
        //event11.setFavourite(false);
        eventRepository.save(event11);

        // Day 2: 12 September
        Event event12 = new Event();
        event12.setTitle("Sesja techniczna nr 9");
        event12.setSubtitle("Zastosowanie metod sztucznej inteligencji w przetwarzaniu danych multimedialnych");
        event12.setType(EventType.TechnicalSession);
        event12.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 9, 0));
        event12.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 10, 30));
        event12.setBuilding("Centrum Wykładowo-Konferencyjne");
        event12.setRoom("Sala CW-1");
        //event12.setFavourite(false);
        eventRepository.save(event12);

//        Event event13 = new Event();
//        event13.setTitle("Sesja techniczna nr 10");
//        event13.setSubtitle("Aspekty radiowe sieci 5G i 6G – I");
//        event13.setType(EventType.TechnicalSession);
//        event13.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 9, 0));
//        event13.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 10, 30));
//        event13.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event13.setRoom("Sala 022");
//        event13.setFavourite(false);
//        eventRepository.save(event13);
//
//        Event event14 = new Event();
//        event14.setTitle("Sesja techniczna nr 11");
//        event14.setSubtitle("Bezprzewodowe sieci lokalne, sensorowe i ad-hoc");
//        event14.setType(EventType.TechnicalSession);
//        event14.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 9, 0));
//        event14.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 10, 30));
//        event14.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event14.setRoom("Sala 023");
//        event14.setFavourite(false);
//        eventRepository.save(event14);
//
//        Event event15 = new Event();
//        event15.setTitle("Sesja techniczna nr 12");
//        event15.setSubtitle("Jakość usług i niezawodność sieci teleinformatycznych");
//        event15.setType(EventType.TechnicalSession);
//        event15.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 9, 0));
//        event15.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 10, 30));
//        event15.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event15.setRoom("Sala 027");
//        event15.setFavourite(false);
//        eventRepository.save(event15);
//
//        Event event16 = new Event();
//        event16.setTitle("Referat plenarny nr 3");
//        event16.setSubtitle("Grzegorz Borowik, Anna Felkner, Adam Kozakiewicz");
//        event16.setType(EventType.PlenarySession);
//        event16.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 11, 0));
//        event16.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 11, 45));
//        event16.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event16.setRoom("Sala CW-1");
//        event16.setFavourite(false);
//        eventRepository.save(event16);
//
//        Event event17 = new Event();
//        event17.setTitle("Referat plenarny nr 4");
//        event17.setSubtitle("Andrzej Bęben");
//        event17.setType(EventType.PlenarySession);
//        event17.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 11, 45));
//        event17.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 12, 30));
//        event17.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event17.setRoom("Sala CW-1");
//        event17.setFavourite(false);
//        eventRepository.save(event17);
//
//        Event event18 = new Event();
//        event18.setTitle("Panel satelitarny");
//        event18.setSubtitle("");
//        event18.setType(EventType.Other);
//        event18.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 13, 45));
//        event18.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 14, 30));
//        event18.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event18.setRoom("Sala CW-1");
//        event18.setFavourite(false);
//        eventRepository.save(event18);
//
//        Event event19 = new Event();
//        event19.setTitle("Sesja firmowa");
//        event19.setSubtitle("");
//        event19.setType(EventType.Other);
//        event19.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 14, 30));
//        event19.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 15, 30));
//        event19.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event19.setRoom("Sala CW-1");
//        event19.setFavourite(false);
//        eventRepository.save(event19);
//
//        Event event20 = new Event();
//        event20.setTitle("Sekcja telekomunikacji");
//        event20.setSubtitle("");
//        event20.setType(EventType.Other);
//        event20.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 14, 30));
//        event20.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 16, 0));
//        event20.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event20.setRoom("Sala 056");
//        event20.setFavourite(false);
//        eventRepository.save(event20);
//
//        Event event21 = new Event();
//        event21.setTitle("Sesja techniczna nr 13");
//        event21.setSubtitle("Technika antenowa i mikrofalowa");
//        event21.setType(EventType.TechnicalSession);
//        event21.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 16, 0));
//        event21.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 17, 30));
//        event21.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event21.setRoom("Sala CW-1");
//        event21.setFavourite(false);
//        eventRepository.save(event21);
//
//        Event event22 = new Event();
//        event22.setTitle("Sesja techniczna nr 14");
//        event22.setSubtitle("Aspekty radiowe sieci 5G i 6G – II");
//        event22.setType(EventType.TechnicalSession);
//        event22.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 16, 0));
//        event22.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 17, 30));
//        event22.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event22.setRoom("Sala 022");
//        event22.setFavourite(false);
//        eventRepository.save(event22);
//
//        Event event23 = new Event();
//        event23.setTitle("Sesja techniczna nr 15");
//        event23.setSubtitle("Propagacja fal radiowych");
//        event23.setType(EventType.TechnicalSession);
//        event23.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 16, 0));
//        event23.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 17, 30));
//        event23.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event23.setRoom("Sala 023");
//        event23.setFavourite(false);
//        eventRepository.save(event23);
//
//        Event event24 = new Event();
//        event24.setTitle("Sesja techniczna nr 16");
//        event24.setSubtitle("Badanie jakości treści multimedialnych");
//        event24.setType(EventType.TechnicalSession);
//        event24.setDateTimeStart(LocalDateTime.of(2024, 9, 12, 16, 0));
//        event24.setDateTimeEnd(LocalDateTime.of(2024, 9, 12, 17, 30));
//        event24.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event24.setRoom("Sala 027");
//        event24.setFavourite(false);
//        eventRepository.save(event24);
//
//        // Day 3: 13 September
//        Event event25 = new Event();
//        event25.setTitle("Sesja techniczna nr 17");
//        event25.setSubtitle("Systemy i sieci komórkowe");
//        event25.setType(EventType.TechnicalSession);
//        event25.setDateTimeStart(LocalDateTime.of(2024, 9, 13, 9, 0));
//        event25.setDateTimeEnd(LocalDateTime.of(2024, 9, 13, 10, 30));
//        event25.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event25.setRoom("Sala CW-1");
//        event25.setFavourite(false);
//        eventRepository.save(event25);
//
//        Event event26 = new Event();
//        event26.setTitle("Sesja techniczna nr 18");
//        event26.setSubtitle("Systemy i sieci radiokomunikacyjne dla zastosowań specjalnych");
//        event26.setType(EventType.TechnicalSession);
//        event26.setDateTimeStart(LocalDateTime.of(2024, 9, 13, 9, 0));
//        event26.setDateTimeEnd(LocalDateTime.of(2024, 9, 13, 10, 30));
//        event26.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event26.setRoom("Sala 022");
//        event26.setFavourite(false);
//        eventRepository.save(event26);
//
//        Event event27 = new Event();
//        event27.setTitle("Sesja techniczna nr 19");
//        event27.setSubtitle("Systemy i sieci radiofoniczne, telewizyjne i satelitarne");
//        event27.setType(EventType.TechnicalSession);
//        event27.setDateTimeStart(LocalDateTime.of(2024, 9, 13, 9, 0));
//        event27.setDateTimeEnd(LocalDateTime.of(2024, 9, 13, 10, 30));
//        event27.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event27.setRoom("Sala 023");
//        event27.setFavourite(false);
//        eventRepository.save(event27);
//
//        Event event28 = new Event();
//        event28.setTitle("Sesja techniczna nr 20");
//        event28.setSubtitle("Kompresja wizji");
//        event28.setType(EventType.TechnicalSession);
//        event28.setDateTimeStart(LocalDateTime.of(2024, 9, 13, 9, 0));
//        event28.setDateTimeEnd(LocalDateTime.of(2024, 9, 13, 10, 30));
//        event28.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event28.setRoom("Sala 027");
//        event28.setFavourite(false);
//        eventRepository.save(event28);
//
//        Event event29 = new Event();
//        event29.setTitle("Sesja techniczna nr 21");
//        event29.setSubtitle("Rozwiązania dla systemów i sieci chmurowych");
//        event29.setType(EventType.TechnicalSession);
//        event29.setDateTimeStart(LocalDateTime.of(2024, 9, 13, 9, 0));
//        event29.setDateTimeEnd(LocalDateTime.of(2024, 9, 13, 10, 30));
//        event29.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event29.setRoom("Sala 029");
//        event29.setFavourite(false);
//        eventRepository.save(event29);
//
//        Event event30 = new Event();
//        event30.setTitle("Referat plenarny nr 5");
//        event30.setSubtitle("Marcin Dryjański");
//        event30.setType(EventType.PlenarySession);
//        event30.setDateTimeStart(LocalDateTime.of(2024, 9, 13, 11, 0));
//        event30.setDateTimeEnd(LocalDateTime.of(2024, 9, 13, 11, 45));
//        event30.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event30.setRoom("Sala CW-1");
//        event30.setFavourite(false);
//        eventRepository.save(event30);
//
//        Event event31 = new Event();
//        event31.setTitle("Referat plenarny nr 6");
//        event31.setSubtitle("Paweł Kryszkiewicz");
//        event31.setType(EventType.PlenarySession);
//        event31.setDateTimeStart(LocalDateTime.of(2024, 9, 13, 11, 45));
//        event31.setDateTimeEnd(LocalDateTime.of(2024, 9, 13, 12, 30));
//        event31.setBuilding("Centrum Wykładowo-Konferencyjne");
//        event31.setRoom("Sala CW-1");
//        event31.setFavourite(false);
//        eventRepository.save(event31);

        System.out.println("✅ Wszystkie eventy zostały dodane do bazy danych!");


        List<Event> allEvents = eventRepository.findAll();

        Report report1 = new Report();
        report1.setTitle("Analiza wydajności algorytmów uczenia maszynowego w sieciach 5G");
        report1.setAuthors(List.of("Dr Paweł Nowak", "Prof. Anna Wiśniewska"));
        report1.setDescription("Badanie porównawcze wydajności różnych algorytmów ML w środowisku sieci 5G");
        report1.setPdfUrl("https://example.com/reports/ml_5g_performance.pdf");
        report1.setKeywords(List.of("uczenie maszynowe", "5G", "wydajność", "algorytmy", "sieci telekomunikacyjne"));
        report1.setEvent(allEvents.get(1)); // Sesja techniczna nr 1 - AI w systemach radiowych
        reportRepository.save(report1);


        Report report2 = new Report();
        report2.setTitle("Bezpieczeństwo danych w chmurze obliczeniowej");
        report2.setAuthors(List.of("Mgr inż. Tomasz Kowalczyk"));
        report2.setDescription("Przegląd współczesnych metod zabezpieczania danych w infrastrukturze chmurowej");
        report2.setPdfUrl("https://example.com/reports/cloud_security.pdf");
        report2.setKeywords(List.of("bezpieczeństwo", "chmura obliczeniowa", "dane", "szyfrowanie"));
        report2.setEvent(allEvents.get(2)); // Sesja techniczna nr 2 - Bezpieczeństwo sieci
        reportRepository.save(report2);


        Report report3 = new Report();
        report3.setTitle("Implementacja systemów GPS w autonomicznych pojazdach");
        report3.setAuthors(List.of("Dr Marcin Lewandowski", "Inż. Katarzyna Zając"));
        report3.setDescription("Studium przypadku zastosowania technologii GPS w pojazdach autonomicznych");
        report3.setPdfUrl("https://example.com/reports/gps_autonomous_vehicles.pdf");
        report3.setKeywords(List.of("GPS", "pojazdy autonomiczne", "nawigacja", "systemy lokalizacyjne"));
        report3.setEvent(allEvents.get(3)); // Sesja techniczna nr 3 - Systemy radionawigacyjne
        reportRepository.save(report3);


        Report report4 = new Report();
        report4.setTitle("Optymalizacja architektury sieci rdzeniowej 6G");
        report4.setAuthors(List.of("Prof. Janusz Kaczmarek"));
        report4.setDescription("Propozycja nowej architektury sieci rdzeniowej dla technologii 6G");
        report4.setPdfUrl("https://example.com/reports/6g_core_optimization.pdf");
        report4.setKeywords(List.of("6G", "sieć rdzeniowa", "optymalizacja", "architektura"));
        report4.setEvent(allEvents.get(4)); // Sesja techniczna nr 4 - Aspekty sieci rdzeniowych 5G i 6G
        reportRepository.save(report4);


        Report report5 = new Report();
        report5.setTitle("Zastosowanie blockchain w systemach IoT");
        report5.setAuthors(List.of("Dr hab. Aleksandra Nowicka", "Mgr Piotr Wójcik"));
        report5.setDescription("Analiza możliwości wykorzystania technologii blockchain w Internet of Things");
        report5.setPdfUrl("https://example.com/reports/blockchain_iot.pdf");
        report5.setKeywords(List.of("blockchain", "IoT", "Internet rzeczy", "bezpieczeństwo", "decentralizacja"));
        report5.setEvent(allEvents.get(10)); // Sesja techniczna nr 8 - Aspekty sieciowe IoT
        reportRepository.save(report5);


        Report report6 = new Report();
        report6.setTitle("Kryptografia post-kwantowa w komunikacji bezprzewodowej");
        report6.setAuthors(List.of("Prof. dr hab. Michał Górski"));
        report6.setDescription("Przegląd algorytmów kryptograficznych odpornych na ataki komputerów kwantowych");
        report6.setPdfUrl("https://example.com/reports/post_quantum_crypto.pdf");
        report6.setKeywords(List.of("kryptografia", "kwantowa", "bezpieczeństwo", "algorytmy", "komunikacja"));
        report6.setEvent(allEvents.get(8)); // Sesja techniczna nr 6 - Kryptografia i cyberbezpieczeństwo
        reportRepository.save(report6);


        Report report7 = new Report();
        report7.setTitle("Analiza propagacji fal milimetrowych w środowisku miejskim");
        report7.setAuthors(List.of("Dr Inż. Robert Szymański", "Mgr Anna Pietrzak"));
        report7.setDescription("Badanie charakterystyk propagacji fal mmWave w gęstej zabudowie miejskiej");
        report7.setPdfUrl("https://example.com/reports/mmwave_urban_propagation.pdf");
        report7.setKeywords(List.of("fale milimetrowe", "propagacja", "środowisko miejskie", "mmWave"));
        report7.setEvent(allEvents.get(0)); // Referat plenarny nr 1 - Fale milimetrowe
        reportRepository.save(report7);


//        Report report8 = new Report();
//        report8.setTitle("Protokoły komunikacji w sieciach sensorowych");
//        report8.setAuthors(List.of("Dr Kamila Zawadzka"));
//        report8.setDescription("Porównanie efektywności różnych protokołów komunikacyjnych w WSN");
//        report8.setPdfUrl("https://example.com/reports/sensor_network_protocols.pdf");
//        report8.setKeywords(List.of("sieci sensorowe", "protokoły", "komunikacja", "WSN", "efektywność"));
//        report8.setEvent(allEvents.get(13)); // Sesja techniczna nr 11 - Bezprzewodowe sieci sensorowe
//        reportRepository.save(report8);


        Report report9 = new Report();
        report9.setTitle("Kompresja wideo w czasie rzeczywistym dla aplikacji mobilnych");
        report9.setAuthors(List.of("Mgr inż. Łukasz Adamczyk", "Dr Monika Jabłońska"));
        report9.setDescription("Nowe algorytmy kompresji wideo zoptymalizowane dla urządzeń mobilnych");
        report9.setPdfUrl("https://example.com/reports/realtime_video_compression.pdf");
        report9.setKeywords(List.of("kompresja wideo", "czas rzeczywisty", "aplikacje mobilne", "algorytmy"));
        report9.setEvent(allEvents.get(11)); // Sesja techniczna nr 9 - AI w przetwarzaniu multimedialnym
        reportRepository.save(report9);


//        Report report10 = new Report();
//        report10.setTitle("Analiza QoS w sieciach telekomunikacyjnych nowej generacji");
//        report10.setAuthors(List.of("Prof. Krzysztof Dudek"));
//        report10.setDescription("Metodyki oceny i zapewnienia jakości usług w nowoczesnych sieciach");
//        report10.setPdfUrl("https://example.com/reports/qos_next_gen_networks.pdf");
//        report10.setKeywords(List.of("QoS", "jakość usług", "sieci telekomunikacyjne", "niezawodność"));
//        report10.setEvent(allEvents.get(14)); // Sesja techniczna nr 12 - Jakość usług i niezawodność
//        reportRepository.save(report10);
//
//
//        Report report11 = new Report();
//        report11.setTitle("Metryki niezawodności w sieciach krytycznych");
//        report11.setAuthors(List.of("Dr hab. Ewa Malinowska", "Inż. Bartosz Kowal"));
//        report11.setDescription("Definiowanie i pomiar niezawodności w systemach telekomunikacyjnych o krytycznym znaczeniu");
//        report11.setPdfUrl("https://example.com/reports/reliability_metrics.pdf");
//        report11.setKeywords(List.of("niezawodność", "metryki", "systemy krytyczne", "pomiary"));
//        report11.setEvent(allEvents.get(14)); // Sesja techniczna nr 12 - Jakość usług i niezawodność
//        reportRepository.save(report11);
//
//
//        Report report12 = new Report();
//        report12.setTitle("Techniki antenowe MIMO w systemach 5G");
//        report12.setAuthors(List.of("Dr Inż. Grzegorz Nowicki"));
//        report12.setDescription("Projektowanie i optymalizacja anten MIMO dla sieci piątej generacji");
//        report12.setPdfUrl("https://example.com/reports/mimo_antennas_5g.pdf");
//        report12.setKeywords(List.of("MIMO", "anteny", "5G", "projektowanie", "optymalizacja"));
//        report12.setEvent(allEvents.get(20)); // Sesja techniczna nr 13 - Technika antenowa
//        reportRepository.save(report12);
//
//
//        Report report13 = new Report();
//        report13.setTitle("Beamforming w systemach komunikacji bezprzewodowej");
//        report13.setAuthors(List.of("Mgr Natalia Wróbel", "Dr Artur Mazur"));
//        report13.setDescription("Zaawansowane techniki kształtowania wiązki w komunikacji radiowej");
//        report13.setPdfUrl("https://example.com/reports/beamforming_wireless.pdf");
//        report13.setKeywords(List.of("beamforming", "komunikacja bezprzewodowa", "kształtowanie wiązki"));
//        report13.setEvent(allEvents.get(20)); // Sesja techniczna nr 13 - Technika antenowa
//        reportRepository.save(report13);
//
//
//        Report report14 = new Report();
//        report14.setTitle("Protokoły routingu w sieciach ad-hoc");
//        report14.setAuthors(List.of("Prof. Stanisław Kowalski"));
//        report14.setDescription("Porównanie wydajności protokołów routingu w sieciach MANET");
//        report14.setPdfUrl("https://example.com/reports/adhoc_routing_protocols.pdf");
//        report14.setKeywords(List.of("routing", "sieci ad-hoc", "MANET", "protokoły"));
//        report14.setEvent(allEvents.get(13)); // Sesja techniczna nr 11 - Sieci ad-hoc
//        reportRepository.save(report14);
//
//
//        Report report15 = new Report();
//        report15.setTitle("Satelitarne systemy komunikacyjne nowej generacji");
//        report15.setAuthors(List.of("Dr hab. Jerzy Białek", "Mgr inż. Sylwia Korek"));
//        report15.setDescription("Przegląd technologii satelitarnych dla komunikacji globalnej");
//        report15.setPdfUrl("https://example.com/reports/next_gen_satellite_systems.pdf");
//        report15.setKeywords(List.of("systemy satelitarne", "komunikacja globalna", "technologie"));
//        report15.setEvent(allEvents.get(26)); // Sesja techniczna nr 19 - Systemy satelitarne
//        reportRepository.save(report15);
//
//
//        Report report16 = new Report();
//        report16.setTitle("Cyfrowe przetwarzanie sygnałów w radiokomunikacji");
//        report16.setAuthors(List.of("Dr Inż. Marek Zieliński"));
//        report16.setDescription("Nowoczesne metody DSP w systemach radiokomunikacyjnych");
//        report16.setPdfUrl("https://example.com/reports/dsp_radio_communication.pdf");
//        report16.setKeywords(List.of("DSP", "przetwarzanie sygnałów", "radiokomunikacja", "cyfrowe"));
//        report16.setEvent(allEvents.get(25)); // Sesja techniczna nr 18 - Systemy radiokomunikacyjne specjalne
//        reportRepository.save(report16);
//
//
//        Report report17 = new Report();
//        report17.setTitle("Architektura sieci Edge Computing");
//        report17.setAuthors(List.of("Prof. Danuta Majewska", "Dr Wojciech Król"));
//        report17.setDescription("Projektowanie infrastruktury edge computing dla aplikacji IoT");
//        report17.setPdfUrl("https://example.com/reports/edge_computing_architecture.pdf");
//        report17.setKeywords(List.of("edge computing", "architektura", "IoT", "infrastruktura"));
//        report17.setEvent(allEvents.get(28)); // Sesja techniczna nr 21 - Systemy chmurowe
//        reportRepository.save(report17);
//
//
//        Report report18 = new Report();
//        report18.setTitle("Algorytmy kompresji bezstratnej dla danych medycznych");
//        report18.setAuthors(List.of("Mgr inż. Agnieszka Lis"));
//        report18.setDescription("Specjalizowane metody kompresji obrazów medycznych");
//        report18.setPdfUrl("https://example.com/reports/medical_data_compression.pdf");
//        report18.setKeywords(List.of("kompresja", "dane medyczne", "obrazy", "bezstratna"));
//        report18.setEvent(allEvents.get(27)); // Sesja techniczna nr 20 - Kompresja wizji
//        reportRepository.save(report18);
//
//
//        Report report19 = new Report();
//        report19.setTitle("Sieci komórkowe w środowisku przemysłowym");
//        report19.setAuthors(List.of("Dr hab. Ryszard Pawlak", "Inż. Magdalena Nowak"));
//        report19.setDescription("Wdrożenie technologii 5G w aplikacjach przemysłu 4.0");
//        report19.setPdfUrl("https://example.com/reports/cellular_industrial_networks.pdf");
//        report19.setKeywords(List.of("sieci komórkowe", "przemysł 4.0", "5G", "aplikacje przemysłowe"));
//        report19.setEvent(allEvents.get(24)); // Sesja techniczna nr 17 - Systemy komórkowe
//        reportRepository.save(report19);
//
//
//        Report report20 = new Report();
//        report20.setTitle("Protokoły bezpieczeństwa w komunikacji M2M");
//        report20.setAuthors(List.of("Dr Inż. Tomasz Czarnecki"));
//        report20.setDescription("Analiza bezpieczeństwa komunikacji machine-to-machine");
//        report20.setPdfUrl("https://example.com/reports/m2m_security_protocols.pdf");
//        report20.setKeywords(List.of("M2M", "bezpieczeństwo", "protokoły", "machine-to-machine"));
//        report20.setEvent(allEvents.get(10)); // Sesja techniczna nr 8 - IoT
//        reportRepository.save(report20);
//
//
//        Report report21 = new Report();
//        report21.setTitle("Interferometry radarowe w systemach lokalizacyjnych");
//        report21.setAuthors(List.of("Prof. Zbigniew Kowalczyk", "Dr Joanna Michalska"));
//        report21.setDescription("Zastosowanie interferometrii w precyzyjnych systemach radiolokalizacyjnych");
//        report21.setPdfUrl("https://example.com/reports/radar_interferometry.pdf");
//        report21.setKeywords(List.of("interferometria", "radar", "lokalizacja", "precyzja"));
//        report21.setEvent(allEvents.get(9)); // Sesja techniczna nr 7 - Systemy radiolokalizacyjne II
//        reportRepository.save(report21);
//
//
//        Report report22 = new Report();
//        report22.setTitle("Adaptacyjne kodowanie kanałowe w sieciach bezprzewodowych");
//        report22.setAuthors(List.of("Mgr inż. Karol Wesołowski"));
//        report22.setDescription("Dynamiczne dostosowywanie parametrów kodowania do warunków kanału");
//        report22.setPdfUrl("https://example.com/reports/adaptive_channel_coding.pdf");
//        report22.setKeywords(List.of("kodowanie kanałowe", "adaptacyjne", "sieci bezprzewodowe"));
//        report22.setEvent(allEvents.get(12)); // Sesja techniczna nr 10 - Aspekty radiowe 5G/6G I
//        reportRepository.save(report22);
//
//
//        Report report23 = new Report();
//        report23.setTitle("Modelowanie propagacji w środowiskach indoor");
//        report23.setAuthors(List.of("Dr hab. Elżbieta Dąbrowska"));
//        report23.setDescription("Modele propagacyjne dla komunikacji wewnątrz budynków");
//        report23.setPdfUrl("https://example.com/reports/indoor_propagation_modeling.pdf");
//        report23.setKeywords(List.of("propagacja", "indoor", "modelowanie", "budynki"));
//        report23.setEvent(allEvents.get(22)); // Sesja techniczna nr 15 - Propagacja fal radiowych
//        reportRepository.save(report23);
//
//
//        Report report24 = new Report();
//        report24.setTitle("Technologie transmisji telewizyjnej DVB-T2");
//        report24.setAuthors(List.of("Inż. Marek Wilczyński", "Dr Anna Krawczyk"));
//        report24.setDescription("Implementacja i optymalizacja standardu DVB-T2 w Polsce");
//        report24.setPdfUrl("https://example.com/reports/dvb_t2_implementation.pdf");
//        report24.setKeywords(List.of("DVB-T2", "telewizja", "transmisja", "standard"));
//        report24.setEvent(allEvents.get(26)); // Sesja techniczna nr 19 - Systemy telewizyjne
//        reportRepository.save(report24);
//
//
//        Report report25 = new Report();
//        report25.setTitle("Metody oceny jakości wideo w transmisji strumieniowej");
//        report25.setAuthors(List.of("Prof. Andrzej Sobczak"));
//        report25.setDescription("Obiektywne i subiektywne metody oceny jakości strumieni wideo");
//        report25.setPdfUrl("https://example.com/reports/video_quality_assessment.pdf");
//        report25.setKeywords(List.of("jakość wideo", "streaming", "ocena", "metryki"));
//        report25.setEvent(allEvents.get(23)); // Sesja techniczna nr 16 - Jakość treści multimedialnych
//        reportRepository.save(report25);
//
//
//        Report report26 = new Report();
//        report26.setTitle("Perceptualne modele jakości obrazu");
//        report26.setAuthors(List.of("Dr Inż. Katarzyna Jankowska", "Mgr Paweł Koziński"));
//        report26.setDescription("Modele uwzględniające percepcję wzrokową w ocenie jakości obrazu");
//        report26.setPdfUrl("https://example.com/reports/perceptual_image_quality.pdf");
//        report26.setKeywords(List.of("percepcja", "jakość obrazu", "modele", "wzrok"));
//        report26.setEvent(allEvents.get(23)); // Sesja techniczna nr 16 - Jakość treści multimedialnych
//        reportRepository.save(report26);
//
//
//        Report report27 = new Report();
//        report27.setTitle("Zarządzanie zasobami w sieciach 5G");
//        report27.setAuthors(List.of("Dr hab. Piotr Młynarski"));
//        report27.setDescription("Algorytmy alokacji zasobów w heterogenicznych sieciach 5G");
//        report27.setPdfUrl("https://example.com/reports/5g_resource_management.pdf");
//        report27.setKeywords(List.of("zarządzanie zasobami", "5G", "alokacja", "heterogeniczne"));
//        report27.setEvent(allEvents.get(21)); // Sesja techniczna nr 14 - Aspekty radiowe 5G/6G II
//        reportRepository.save(report27);
//
//
//        Report report28 = new Report();
//        report28.setTitle("Cyberbezpieczeństwo w infrastrukturze krytycznej");
//        report28.setAuthors(List.of("Prof. dr hab. Jacek Mazurek", "Dr Beata Król"));
//        report28.setDescription("Zabezpieczanie systemów telekomunikacyjnych infrastruktury krytycznej");
//        report28.setPdfUrl("https://example.com/reports/critical_infrastructure_security.pdf");
//        report28.setKeywords(List.of("cyberbezpieczeństwo", "infrastruktura krytyczna", "zabezpieczenia"));
//        report28.setEvent(allEvents.get(15)); // Referat plenarny nr 3 - Cyberbezpieczeństwo
//        reportRepository.save(report28);
//
//
//        Report report29 = new Report();
//        report29.setTitle("Zastosowanie sztucznej inteligencji w analizie ruchu sieciowego");
//        report29.setAuthors(List.of("Mgr inż. Damian Wieczorek"));
//        report29.setDescription("Wykorzystanie ML do predykcji i optymalizacji ruchu w sieciach");
//        report29.setPdfUrl("https://example.com/reports/ai_network_traffic_analysis.pdf");
//        report29.setKeywords(List.of("sztuczna inteligencja", "analiza ruchu", "predykcja", "optymalizacja"));
//        report29.setEvent(allEvents.get(7)); // Sesja techniczna nr 5 - AI w systemach radiowych II
//        reportRepository.save(report29);
//
//
//        Report report30 = new Report();
//        report30.setTitle("Przyszłość technologii 6G - wizja i wyzwania");
//        report30.setAuthors(List.of("Prof. Krystyna Siwek", "Dr hab. Mariusz Kowal", "Inż. Aleksandra Nowak"));
//        report30.setDescription("Analiza trendów i przewidywanych kierunków rozwoju technologii 6G");
//        report30.setPdfUrl("https://example.com/reports/6g_future_vision.pdf");
//        report30.setKeywords(List.of("6G", "przyszłość", "technologie", "wizja", "rozwój"));
//        report30.setEvent(allEvents.get(16));
//        reportRepository.save(report30);

        Report report31 = new Report();
        report5.setTitle("Zastosowanie blockchain w systemach IoT");
        report5.setAuthors(List.of("Dr hab. Aleksandra Nowicka", "Mgr Piotr Wójcik"));
        report5.setDescription("Analiza możliwości wykorzystania technologii blockchain w Internet of Things");
        report5.setPdfUrl("https://example.com/reports/blockchain_iot.pdf");
        report5.setKeywords(List.of("blockchain", "IoT", "Internet rzeczy", "bezpieczeństwo", "decentralizacja"));
        report5.setEvent(allEvents.get(2)); // Sesja techniczna nr 8 - Aspekty sieciowe IoT
        reportRepository.save(report31);

        Report report32 = new Report();
        report5.setTitle("Zastosowanie blockchain w systemach IoT");
        report5.setAuthors(List.of("Dr hab. Aleksandra Nowicka", "Mgr Piotr Wójcik"));
        report5.setDescription("Analiza możliwości wykorzystania technologii blockchain w Internet of Things");
        report5.setPdfUrl("https://example.com/reports/blockchain_iot.pdf");
        report5.setKeywords(List.of("blockchain", "IoT", "Internet rzeczy", "bezpieczeństwo", "decentralizacja"));
        report5.setEvent(allEvents.get(4)); // Sesja techniczna nr 8 - Aspekty sieciowe IoT
        reportRepository.save(report32);

        System.out.println("Wszystkie 30 raportów zostało dodanych do eventów!");


    }
}
