package com.example.demo.service;

import com.example.demo.dto.TableReservationDto;
import com.example.demo.entity.TableReservation;
import com.example.demo.repository.TableReservationRepository;
import com.example.demo.utils.ConversionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TableReservationService {

    @Autowired
    TableService tableService;

    @Autowired
    TableReservationRepository tableReservationRepository;

    @Autowired
    AppUserService appUserService;

    @Autowired
    ConversionsUtil conversionsUtil;

    public long countEntriesForDate(LocalDateTime reservationDateTime) {
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalDateTime startOfDay = reservationDate.atStartOfDay();
        LocalDateTime endOfDay = reservationDate.atTime(23, 59, 59);
        Long occupiedSeats = tableReservationRepository.findOccupiedSeatsForDay(startOfDay, endOfDay);
        if (occupiedSeats == null) {
            occupiedSeats = 0L;
        }
        return occupiedSeats;
    }

    public LocalDateTime convertStringToLocalDateTime(String stringDateTime) {
        LocalDateTime localDateTime = conversionsUtil.convertStringToLocalDateTime(stringDateTime);
        return localDateTime;
    }

    private Long occupiedSeatsPostReservation(TableReservationDto tableReservationDto) {
        Long preOccupiedSeats = 0L;
        if (countEntriesForDate(convertStringToLocalDateTime(tableReservationDto.getReservationDateTime())) > 0) {
            preOccupiedSeats = countEntriesForDate(convertStringToLocalDateTime(tableReservationDto.getReservationDateTime()));
        }
        Long postOccupiedSeats = preOccupiedSeats + tableReservationDto.getGuestsNo();
        return postOccupiedSeats;
    }

    public Long availableSeats(TableReservationDto tableReservationDto) {

        Long totalSeatsNumber = tableService.totalSeatsNo();
        Long occupiedSeats = occupiedSeatsPostReservation(tableReservationDto);
        Long availableSeatsNo = totalSeatsNumber - occupiedSeats;

        return availableSeatsNo;
    }

    public Long availableSeatsPreReservation(TableReservationDto tableReservationDto) {
        Long totalSeatsNumber = tableService.totalSeatsNo();
        Long occupiedSeats = countEntriesForDate(conversionsUtil.convertStringToLocalDateTime(tableReservationDto.getReservationDateTime()));
        if (occupiedSeats == null) {
            occupiedSeats = 0L;
        }
        Long availableSeats = totalSeatsNumber - occupiedSeats;
        return availableSeats;
    }

    public void saveTableReservation(TableReservationDto tableReservationDto) {
        TableReservation tableReservation = new TableReservation();

        tableReservation.setReservationDateTime(convertStringToLocalDateTime(tableReservationDto.getReservationDateTime()));
        tableReservation.setName(tableReservationDto.getName());
        tableReservation.setPhone(tableReservationDto.getPhone());
        tableReservation.setGuestsNo(tableReservationDto.getGuestsNo());
        tableReservationDto.setReserved(true);
        tableReservation.setReserved(tableReservationDto.getReserved());

        tableReservationRepository.save(tableReservation);
    }

    public List<TableReservation> findAllReservations() {
        return tableReservationRepository.findAll();
    }

    public void deleteReservation(Long id) {
        tableReservationRepository.deleteById(id);
    }

    public List<TableReservation> showTable() {
        return tableReservationRepository.findAll();
    }

    public List<TableReservation> getReservationOlderThan4h() {
        return tableReservationRepository.getReservationOlderThan4h(LocalDateTime.now().minus(4, ChronoUnit.HOURS));
    }
}

