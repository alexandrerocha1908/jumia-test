package com.test.jumia.ecommerce.entity;

import com.test.jumia.ecommerce.dto.BuyProductRequest;
import com.test.jumia.ecommerce.dto.BuyProductResponse;

import java.util.ArrayList;
import java.util.List;

public abstract class ProductUtils {
    public static List<Product> buildProductList() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .sku("sku 1")
                .country("country")
                .name("name 1")
                .stockChange("1")
                .build());
        products.add(Product.builder()
                .sku("sku 2")
                .country("country")
                .name("name 2")
                .stockChange("2")
                .build());

        return products;
    }

    public static Product buildProduct(String stockChange) {
        return Product.builder()
                .sku("sku 1")
                .country("country")
                .name("name 1")
                .stockChange(stockChange)
                .build();
    }

    public static BuyProductRequest buildProductRequest(Integer stockChange) {
        return BuyProductRequest.builder()
                .sku("sku")
                .country("country")
                .stockChange(stockChange)
                .build();
    }

    public static List<BuyProductResponse> buildListBuyProductResponse() {
        List<BuyProductResponse> buyProductResponses = new ArrayList<>();
        buyProductResponses.add(BuyProductResponse.builder()
                .sku("sku")
                .country("country")
                .name("name")
                .stockChange(1)
                .build());

        return buyProductResponses;
    }

    public static BuyProductResponse buildBuyProductResponse(Integer stockChange) {
        return BuyProductResponse.builder()
                .sku("sku")
                .country("country")
                .name("name")
                .stockChange(stockChange)
                .build();
    }
}
