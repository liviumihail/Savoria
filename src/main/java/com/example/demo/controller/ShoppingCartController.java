package com.example.demo.controller;

import com.example.demo.dto.ShoppingCartDto;
import com.example.demo.dto.TableReservationDto;
import com.example.demo.entity.Products;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.entity.ShoppingCartProducts;
import com.example.demo.service.ProductsService;
import com.example.demo.service.ShoppingCartProductsService;
import com.example.demo.service.ShoppingCartService;
import jdk.jfr.Unsigned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShoppingCartController {

    private ShoppingCart shoppingCart = new ShoppingCart();

    public static Boolean orderFinished=false; //de scos staticul in cazul in care nu merge

    public static int cartProducts=0;

    private List<Products> productsFromCurrentOrder = new ArrayList<>();

    @Autowired
    ProductsService productsService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ShoppingCartProductsService shoppingCartProductsService;

    @PostMapping("/add-to-cart")
    @ResponseBody
    public void addToCart(@RequestParam("productId") Long productId) {
        ShoppingCartProducts shoppingCartProducts = new ShoppingCartProducts();
        shoppingCartProducts.setProductsId(productId);
        shoppingCartProductsService.save(shoppingCartProducts);
        cartProducts++;
        orderFinished=false;
    }

    @GetMapping("/shoppingCart")
    private String shoppingCart(Model model) {
        if (orderFinished) {
            shoppingCart = new ShoppingCart();
            shoppingCartProductsService.deleteAll();
        }
        if (!orderFinished) {
            List<Long> productsIds = shoppingCartProductsService.getProductsIds();
            List<Products> products = new ArrayList<>();
            for (Long id : productsIds) {
                products.add(productsService.getProductsById(id));
            }
            model.addAttribute("products", products);
            model.addAttribute("finalPrice", finalPrice());
        }
        model.addAttribute("cartProducts", shoppingCartProductsService.countProducts());
        return "shoppingCart";
    }

    @GetMapping(value = "/deleteFromCart/{id}")
    public String deleteTicket(@PathVariable(name = "id") Long id) {
        shoppingCartProductsService.deleteProduct(id);
        cartProducts--;
        return "redirect:/shoppingCart";
    }

    private Double finalPrice() {
        Double finalPrice =0d;
        for (Long id : shoppingCartProductsService.getProductsIds()) {
            finalPrice+=productsService.getProductsById(id).getPrice();
        }
        return finalPrice;
    }

    @PostMapping("/shoppingCart")
    private String saveOrder(@ModelAttribute("shoppingCart") ShoppingCartDto shoppingCartDto) {
        saveShoppingCart(shoppingCartDto);
        List<Long> productsIds= shoppingCartProductsService.getProductsIds();
        for (Long productId : productsIds) {
            productsFromCurrentOrder.add(productsService.getProductsById(productId));
            shoppingCartProductsService.deleteProduct(productId);
        }
        return "redirect:/orderConfirmation";
    }

    public void saveShoppingCart(ShoppingCartDto shoppingCartDto) {
        shoppingCart.setProductsId(shoppingCartProductsService.getProductsIdsAsString());
        shoppingCart.setAddress(shoppingCartDto.getAddress());
        shoppingCart.setPersonalNotes(shoppingCartDto.getPersonalNotes());
        shoppingCart.setTotalPrice(finalPrice());
        shoppingCartService.save(shoppingCart);
    }

    @GetMapping("/orderConfirmation")
    private String orderConfirmation(Model model) {
        orderFinished=true;
        model.addAttribute("products", productsFromCurrentOrder);
        //
        model.addAttribute("mentions", shoppingCart.getPersonalNotes());
        model.addAttribute("address", shoppingCart.getAddress());
        model.addAttribute("finalPrice", shoppingCart.getTotalPrice());
        return "orderConfirmation";
    }
}

