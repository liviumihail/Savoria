package com.example.demo.controller;

import com.example.demo.dto.CollabFormDto;
import com.example.demo.service.CollabFormService;
import com.example.demo.service.ShoppingCartProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CollabController {

    @Autowired
    ShoppingCartProductsService shoppingCartProductsService;

    @Autowired
    CollabFormService collabFormService;

    @GetMapping("collabForm")
    private String collabForm(Model model) {
        model.addAttribute("cartProducts", shoppingCartProductsService.countProducts());
        return "collabForm";
    }

    @PostMapping("/collabForm")
    public String findTable(@Valid CollabFormDto collabFormDto) {
        collabFormService.save(collabFormDto);
        return "collabFormConfirm";
    }

    @GetMapping("collabFormConfirm")
    private String collabFormConfirm(Model model) {
        model.addAttribute("cartProducts", shoppingCartProductsService.countProducts());
        return "collabFormConfirm";
    }
}
