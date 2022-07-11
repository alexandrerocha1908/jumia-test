package com.test.jumia.ecommerce.clients;

import com.test.jumia.ecommerce.dto.BuyProductRequest;
import com.test.jumia.ecommerce.dto.BuyProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "ProductClient", url = "http://localhost:8081/product")
public interface ProductCatalogClient {

    @GetMapping(value = "/list")
    List<BuyProductResponse> listAllProducts();

    @GetMapping(value = "/search/{productName}")
    List<BuyProductResponse> search(@PathVariable("productName") String productName);

    @GetMapping(value = "/{sku}/{country}")
    BuyProductResponse getProduct(@PathVariable("sku") String sku, @PathVariable("country") String country);

    @PostMapping(value = "/check-availability")
    Boolean checkAvailability(@RequestBody BuyProductRequest buyProductRequest);

    @PostMapping(value = "/buy")
    ResponseEntity buy(@RequestBody BuyProductRequest buyProductRequest);
}
