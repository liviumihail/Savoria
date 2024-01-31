package com.example.demo.Jobs;

import com.example.demo.entity.TableReservation;
import com.example.demo.repository.TableReservationRepository;
import com.example.demo.service.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledJobs {

    @Autowired
    TableReservationService tableReservationService;

    @Autowired
    TableReservationRepository tableReservationRepository;

    @Scheduled(cron = "0 0 * * * *") //la inceputul fiecarei ore
     void deleteReservationsAfter4h() {
        List<TableReservation> tableReservationsOlderThan4h = tableReservationService.getReservationOlderThan4h();
        for (TableReservation tableReservation : tableReservationsOlderThan4h) {
            tableReservationRepository.delete(tableReservation);
        }
    }
}
