package com.example.demo.dto;

public class TableDto {
    private Long id;

    private Integer seatsNumber;

    private Long tablesWithSameSeatsNr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Long getTablesWithSameSeatsNr() {
        return tablesWithSameSeatsNr;
    }

    public void setTablesWithSameSeatsNr(Long tablesWithSameSeatsNr) {
        this.tablesWithSameSeatsNr = tablesWithSameSeatsNr;
    }
}
