package com.example.demo.service;

import com.example.demo.controller.RegistrationController;
import com.example.demo.entity.Products;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    AppUserService appUserService;

    @Autowired
    ProductsService productsService;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

/*    public void saveProducts(List<CartItem> products) {
        shoppingCart.setCartItems(products);
        shoppingCartRepository.save(shoppingCart);
    }*/

/*    public List<Products> getCartItems() {
        List<Products> products = shoppingCartRepository.getProducts();
        return products;
    }*/

    public void save(ShoppingCart shoppingCart) {
        shoppingCart.setClientEmail(appUserService.getLoggedInUser().getEmail());
        shoppingCart.setDateTime(LocalDateTime.now());
        shoppingCartRepository.save(shoppingCart);
    }
}
