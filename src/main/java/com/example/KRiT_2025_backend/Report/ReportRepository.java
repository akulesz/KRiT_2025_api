package com.example.KRiT_2025_backend.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {
    @Modifying
    @Query("UPDATE Report r SET r.event = null WHERE r.event.id = :eventId")
    void clearEventReference(@Param("eventId") UUID eventId);
}
