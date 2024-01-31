package com.example.demo.controller;

import com.example.demo.entity.Products;
import com.example.demo.entity.Review;
import com.example.demo.service.GlobalParametersService;
import com.example.demo.service.ProductsService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.ShoppingCartProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;
import java.util.List;

import static com.example.demo.constants.GlobalParameterConstants.CURRENT_LOGGED_IN_USER_NAME;

@Controller
public class MainController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ShoppingCartProductsService shoppingCartProductsService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GlobalParametersService globalParameterService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Products> products = productsService.findAllProducts();

        int i = 1;
        int j = 1;
        for (Products product : products) {
            if (product.getDrink()) {
                model.addAttribute("drink" + j, product);
                byte[] imageData = getImageData(product.getId());
                String base64ImageData = Base64.getEncoder().encodeToString(imageData);
                String imageSrc = "data:image/jpeg;base64," + base64ImageData;
                model.addAttribute("drinkImageSrc" + j, imageSrc);
                j++;
            }

            if (!product.getDrink()) {
                model.addAttribute("product" + i, product);
                byte[] imageData = getImageData(product.getId());

                String base64ImageData = Base64.getEncoder().encodeToString(imageData);
                String imageSrc = "data:image/jpeg;base64," + base64ImageData;
                model.addAttribute("imageSrc" + i, imageSrc);
                i++;
            }
        }

        model.addAttribute("cartProducts", shoppingCartProductsService.countProducts());

        List<Review> reviews = reviewService.findAllReviews();
        model.addAttribute("reviews", reviews);


        if (CURRENT_LOGGED_IN_USER_NAME == null) {
            model.addAttribute("loggedInUser", "Guest");
        } else {
            model.addAttribute("loggedInUser", CURRENT_LOGGED_IN_USER_NAME);
        }

        return "index";
    }

    @RequestMapping("/index")
    public String index2(Model model) {
        List<Products> products = productsService.findAllProducts();
        int i = 1;
        int j = 1;
        for (Products product : products) {
            if (product.getDrink()) {
                model.addAttribute("drink" + j, product);
                byte[] imageData = getImageData(product.getId());
                String base64ImageData = Base64.getEncoder().encodeToString(imageData);
                String imageSrc = "data:image/jpeg;base64," + base64ImageData;
                model.addAttribute("drinkImageSrc" + j, imageSrc);
                j++;
            }
            if (!product.getDrink()) {
                model.addAttribute("product" + i, product);
                byte[] imageData = getImageData(product.getId());

                String base64ImageData = Base64.getEncoder().encodeToString(imageData);
                String imageSrc = "data:image/jpeg;base64," + base64ImageData;
                model.addAttribute("imageSrc" + i, imageSrc);
                i++;
            }
        }

        List<Review> reviews = reviewService.findAllReviews();
        model.addAttribute("cartProducts", shoppingCartProductsService.countProducts());
        model.addAttribute("reviews", reviews);


        if (CURRENT_LOGGED_IN_USER_NAME == null) {
            model.addAttribute("loggedInUser", "Guest");
        } else {
            model.addAttribute("loggedInUser", CURRENT_LOGGED_IN_USER_NAME);
        }
        return "index";
    }

    private byte[] getImageData(Long id) {

        return productsService.getImageById(id);
        /*String sql = "SELECT image FROM products WHERE id=" + id;
        return jdbcTemplate.queryForObject(sql, byte[].class);*/
    }

    @GetMapping("/components")
    private String components() {
        return "components";
    }


    //new method
       /*List<Products> products = productsService.findAllProducts();
        int i=1;
        int j=1;
        List<Products> drinksList = new ArrayList<>();
        List<Products> productsList = new ArrayList<>();
        for (Products product : products) {
            if (product.getDrink()) {
                drinksList.add(product);
                byte[] imageData = getImageData(product.getId());
                String base64ImageData = Base64.getEncoder().encodeToString(imageData);
                String imageSrc = "data:image/jpeg;base64," + base64ImageData;
                model.addAttribute("drinkImageSrc" + j, imageSrc);
                j++;
            }
            if (!product.getDrink()) {
                productsList.add(product);
                byte[] imageData = getImageData(product.getId());

                String base64ImageData = Base64.getEncoder().encodeToString(imageData);
                String imageSrc = "data:image/jpeg;base64," + base64ImageData;
                model.addAttribute("imageSrc" + i, imageSrc);
                i++;
            }
        }
        model.addAttribute("productsList", productsList);
        model.addAttribute("drinksList", drinksList);*/

    @GetMapping("termsAndConditions")
    public String termsAndConditions() {
        return "termsAndConditions";
    }
}
