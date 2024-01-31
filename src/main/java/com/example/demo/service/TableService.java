package com.example.demo.service;

import com.example.demo.entity.Table;
import com.example.demo.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    @Autowired
    TableRepository tableRepository;

    public void save(Table table){
        tableRepository.save(table);
    }

    public List<Table> findAllTables(){
        return tableRepository.findAll();
    }

    public Long totalSeatsNo() {
        return tableRepository.totalSeatsNo();
    }

}
