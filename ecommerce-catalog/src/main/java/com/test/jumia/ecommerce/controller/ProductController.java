package com.test.jumia.ecommerce.controller;

import com.test.jumia.ecommerce.dto.BuyProductRequest;
import com.test.jumia.ecommerce.dto.BuyProductResponse;
import com.test.jumia.ecommerce.exception.OutOfStockException;
import com.test.jumia.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/list")
    public List<BuyProductResponse> listProducts() {
        List<BuyProductResponse> buyProductResponses = productService.listAllProducts();
        if(!buyProductResponses.isEmpty())
            return buyProductResponses;

        return new ArrayList<>();
    }

    @GetMapping("/search/{productName}")
    public List<BuyProductResponse> searchProduct(@PathVariable String productName) {
        List<BuyProductResponse> productResponses = productService.search(productName);
        if(!productResponses.isEmpty())
            return productResponses;

        return new ArrayList<>();
    }

    @GetMapping("/{sku}/{country}")
    public BuyProductResponse getProduct(@PathVariable String sku, @PathVariable String country) {
        BuyProductResponse buyProductResponse = productService.getProduct(sku, country);
        if (buyProductResponse != null) {
            return buyProductResponse;
        }
        return null;
    }

    @PostMapping("/buy")
    public ResponseEntity buyProduct(@RequestBody BuyProductRequest buyProductDTO) {
        try {
            productService.buyProduct(buyProductDTO);
        }catch (IOException | OutOfStockException e) {
            Map<String, String> mapError = new HashMap();
            mapError.put("message", e.getMessage());
            return new ResponseEntity(mapError, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/check-availabilityx")
    public Boolean checkAvailability(@RequestBody BuyProductRequest buyProductRequest) {
        return productService.checkAvailability(buyProductRequest);
    }
}
