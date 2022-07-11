package com.test.jumia.ecommerce.service;

import com.test.jumia.ecommerce.clients.ProductCatalogClient;
import com.test.jumia.ecommerce.dto.BuyProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCacheService {
    @Autowired
    private ProductCatalogClient productCatalogClient;

    @Cacheable(cacheNames = "listAllProducts", unless = "#result == null")
    public List<BuyProductResponse> listProducts() {
        return productCatalogClient.listAllProducts();
    }

    @Cacheable(cacheNames = "search", key = "{#p0}", unless = "#result == null")
    public List<BuyProductResponse> search(String productName) {
        return productCatalogClient.search(productName);
    }
}
