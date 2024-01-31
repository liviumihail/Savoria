package com.example.demo.dto;

import java.util.List;

public class ShoppingCartDto {

    private Long id;

    private List<ShoppingCartProductsDto> products;

    private Float totalPrice;

    private String address;

    private String personalNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ShoppingCartProductsDto> getProducts() {
        return products;
    }

    public void setProducts(List<ShoppingCartProductsDto> products) {
        this.products = products;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonalNotes() {
        return personalNotes;
    }

    public void setPersonalNotes(String personalNotes) {
        this.personalNotes = personalNotes;
    }
}
