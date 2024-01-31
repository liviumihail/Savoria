package com.example.demo.repository;

import com.example.demo.entity.TableReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TableReservationRepository extends JpaRepository<TableReservation, Long> {

    @Query("SELECT SUM(tableReservation.guestsNo) FROM TableReservation tableReservation where tableReservation.reserved=true and tableReservation.reservationDateTime=?1")
    Long occupiedSeats(LocalDateTime reservationDateTime);

    @Query("select tableReservation from TableReservation tableReservation where tableReservation.reservationDateTime <= ?1")
    List<TableReservation> getReservationOlderThan4h(LocalDateTime localDateTime);

    @Query("SELECT SUM(e.guestsNo) FROM TableReservation e WHERE e.reservationDateTime BETWEEN :startDateTime AND :endDateTime")
    Long findOccupiedSeatsForDay(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
