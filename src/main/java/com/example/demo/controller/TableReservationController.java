package com.example.demo.controller;

import com.example.demo.dto.TableReservationDto;
import com.example.demo.entity.TableReservation;
import com.example.demo.service.AppUserService;
import com.example.demo.service.ShoppingCartProductsService;
import com.example.demo.service.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TableReservationController {

    @Autowired
    AppUserService appUserService;

    @Autowired
    TableReservationService tableReservationService;

    @Autowired
    ShoppingCartProductsService shoppingCartProductsService;

    @PostMapping("/tableReservation")
    public String findTable(@Valid @ModelAttribute("isFree") TableReservationDto tableReservationDto, Model model) {

        Boolean showTable = false;
        List<TableReservation> reservations = tableReservationService.findAllReservations();
        model.addAttribute("reservations", reservations);
        if (reservations.size() > 0) {
            showTable = true;
        }
        model.addAttribute("showTable", showTable);

        //verificarea locurilor libere
        Boolean noTablesLeft = false;
        Long freeSeats = tableReservationService.availableSeats(tableReservationDto);
        if (freeSeats < 0) {
            noTablesLeft = true;
        } else {
            tableReservationService.saveTableReservation(tableReservationDto);
        }

        Long freeSeatsPreReservation = tableReservationService.availableSeatsPreReservation(tableReservationDto);
        model.addAttribute("noTablesLeft", noTablesLeft);
        model.addAttribute("freeSeatsPreReservation", freeSeatsPreReservation);

        return "redirect:/tableReservation";
    }

    @GetMapping("/tableReservation")
    public String tableReservation(Model model) {

        Boolean showTable = false;
        List<TableReservation> reservations = tableReservationService.findAllReservations();
        model.addAttribute("reservations", reservations);
        if (tableReservationService.showTable().size() > 0) {
            showTable = true;
        }
        model.addAttribute("showTable", showTable);
        model.addAttribute("cartProducts", shoppingCartProductsService.countProducts());

        return "tableReservation";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteReservation(@PathVariable(name = "id") Long id) {
        tableReservationService.deleteReservation(id);
        return "redirect:/tableReservation";
    }
}
