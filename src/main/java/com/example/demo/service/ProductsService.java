package com.example.demo.service;

import com.example.demo.entity.Products;
import com.example.demo.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Long.parseLong;

@Service
public class ProductsService {
    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Products> findAllProducts() {
        String sql = "SELECT *,\n" +
                " (SELECT COUNT(*) FROM products t2 WHERE t2.id <= t1.id) AS num_rows\n" +
                "FROM products t1\n" +
                "GROUP BY id\n" +
                "ORDER BY num_rows ASC;";
        List<Products> productsList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class));
        return productsList;
        //return productsRepository.findAllProducts();
    }


/*    public Long verifyIfProductWithIdExists(Long id) {
        return productsRepository.verifyIfProductWithIdExists(id);
    }*/

    public Products getProductsById(Long id) {
        Products products = productsRepository.getProductsById(id);
        return products;
    }

    public void saveProducts(Long id) {

        productsRepository.getProductsById(id);
    }

    public byte[] getImageById(Long id) {
        return productsRepository.getImageById(id);
    }

}
