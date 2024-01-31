package com.example.demo.repository;

import com.example.demo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    @Query("select products.id from Products products where products.id=?1")
    Long verifyIfProductWithIdExists(Long id);

    @Query(value = "SELECT t1 (SELECT COUNT(t2) FROM Products t2 WHERE t2.id <= t1.id) AS num_rows FROM Products t1 GROUP BY id ORDER BY num_rows ASC", nativeQuery = true)
    List<Products> findAllProducts();

    Products getProductsById(Long id);

    @Query(value = "SELECT image from products where id=?1", nativeQuery = true)
    byte[] getImageById(Long id);


}
