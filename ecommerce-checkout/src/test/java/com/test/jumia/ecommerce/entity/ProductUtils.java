package com.test.jumia.ecommerce.entity;

import com.test.jumia.ecommerce.dto.BuyProductRequest;
import com.test.jumia.ecommerce.dto.BuyProductResponse;

import java.util.ArrayList;
import java.util.List;

public abstract class ProductUtils {
    public static List<BuyProductResponse> buildBuyProductResponseList() {
        List<BuyProductResponse> buyProductResponses = new ArrayList<>();
        buyProductResponses.add(BuyProductResponse.builder()
                .sku("sku")
                .name("name")
                .country("country")
                .stockChange(1)
                .build());

        return buyProductResponses;
    }

    public static BuyProductResponse buildBuyProductResponse(Integer stockChange) {
        return BuyProductResponse.builder()
                .sku("sku")
                .name("name")
                .country("country")
                .stockChange(stockChange)
                .build();
    }

    public static BuyProductRequest buildBuyProductRequest() {
        return BuyProductRequest.builder()
                .sku("sku")
                .country("country")
                .stockChange(1)
                .build();
    }
}
