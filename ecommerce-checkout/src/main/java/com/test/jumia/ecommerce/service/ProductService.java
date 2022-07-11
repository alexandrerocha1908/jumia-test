package com.test.jumia.ecommerce.service;

import com.test.jumia.ecommerce.dto.BuyProductRequest;
import com.test.jumia.ecommerce.dto.BuyProductResponse;
import com.test.jumia.ecommerce.dto.CheckAvailabilityResponse;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public List<BuyProductResponse> listAllProducts();

    public List<BuyProductResponse> search(String productName);

    public BuyProductResponse getProduct(String sku, String country);

    public CheckAvailabilityResponse checkAvailability(BuyProductRequest buyProductDTO);

    public void buyProduct(BuyProductRequest buyProductDTO);
}
