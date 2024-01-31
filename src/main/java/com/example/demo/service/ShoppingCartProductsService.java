package com.example.demo.service;

import com.example.demo.entity.ShoppingCartProducts;
import com.example.demo.repository.ShoppingCartProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartProductsService {

    @Autowired
    ShoppingCartProductsRepository shoppingCartProductsRepository;

    public void save(ShoppingCartProducts shoppingCartProducts) {
        shoppingCartProductsRepository.save(shoppingCartProducts);
    }

    public List<Long> getProductsIds () {
        return shoppingCartProductsRepository.getProductsIds();
    }

    public void deleteProduct(Long id) {
        shoppingCartProductsRepository.deleteByProductId(id);
    }

    public String getProductsIdsAsString() {
        return shoppingCartProductsRepository.getProductsIdsAsString();
    }

    public void deleteAll() {
        shoppingCartProductsRepository.deleteAll();
    }

    public Long countProducts() {
        return shoppingCartProductsRepository.countProducts();
    }

}
