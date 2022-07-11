package com.test.jumia.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BuyProductResponse {
    String sku;
    String country;
    String name;
    @JsonProperty("stock_change")
    Integer stockChange;
}
