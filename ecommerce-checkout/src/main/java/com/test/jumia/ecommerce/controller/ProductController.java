package com.test.jumia.ecommerce.controller;

import com.test.jumia.ecommerce.dto.BuyProductRequest;
import com.test.jumia.ecommerce.dto.BuyProductResponse;
import com.test.jumia.ecommerce.dto.CheckAvailabilityResponse;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product-checkout")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/list")
    public ResponseEntity listProducts() {
        List<BuyProductResponse> productResponses = productService.listAllProducts();
        if (!productResponses.isEmpty())
            return new ResponseEntity(productService.listAllProducts(), HttpStatus.OK);

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search/{productName}")
    public ResponseEntity searchProduct(@PathVariable String productName) {
        List<BuyProductResponse> productResponses = productService.search(productName);
        if(!productResponses.isEmpty())
            return new ResponseEntity(productResponses, HttpStatus.OK);

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{sku}/{country}")
    public ResponseEntity getProduct(@PathVariable String sku, @PathVariable String country) {
        BuyProductResponse buyProductResponse = productService.getProduct(sku, country);
        if (buyProductResponse != null)
            return new ResponseEntity(buyProductResponse, HttpStatus.OK);

        Map<String, String> mapError = new HashMap();
        mapError.put("message", "Product not found");
        return new ResponseEntity(mapError, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/buy")
    public ResponseEntity buyProduct(@RequestBody BuyProductRequest buyProductDTO) {
        try {
            productService.buyProduct(buyProductDTO);
        }catch (OutOfStockException e) {
            Map<String, String> mapError = new HashMap();
            mapError.put("message", e.getMessage());
            return new ResponseEntity(mapError, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/check-availability")
    public ResponseEntity checkAvailability(@RequestBody BuyProductRequest buyProductRequest) {
        return new ResponseEntity(productService.checkAvailability(buyProductRequest), HttpStatus.OK);
    }

}
