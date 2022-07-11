package com.test.jumia.ecommerce.service;

import com.test.jumia.ecommerce.clients.ProductCatalogClient;
import com.test.jumia.ecommerce.dto.BuyProductRequest;
import com.test.jumia.ecommerce.dto.BuyProductResponse;
import com.test.jumia.ecommerce.dto.CheckAvailabilityResponse;
import com.test.jumia.ecommerce.exception.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductCatalogClient productCatalogClient;

    @Autowired
    ProductCacheService productCacheService;

    @Override
    public List<BuyProductResponse> listAllProducts() {
        return productCacheService.listProducts();
    }

    @Override
    public List<BuyProductResponse> search(String productName) {
        return productCacheService.search(productName);
    }

    @Override
    public BuyProductResponse getProduct(String sku, String country) {
        return productCatalogClient.getProduct(sku, country);
    }

    @Override
    public void buyProduct(BuyProductRequest buyProductDTO) throws OutOfStockException {
//        Call payment api, if is approved, send infos to ERP (where csv batch is generated), if is approved call the buy in catalog
        productCatalogClient.buy(buyProductDTO);
    }

    @Override
    public CheckAvailabilityResponse checkAvailability(BuyProductRequest buyProductDTO) {
        return CheckAvailabilityResponse.builder()
                .isAvailable(productCatalogClient.checkAvailability(buyProductDTO))
                .build();
    }
}
