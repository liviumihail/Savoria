package com.example.demo.repository;

import com.example.demo.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TableRepository extends JpaRepository<Table, Long> {

    @Query("SELECT SUM(table.seatsNumber) FROM Table table")
    Long totalSeatsNo();
}
