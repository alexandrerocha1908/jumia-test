package com.test.jumia.ecommerce.controller;

import com.test.jumia.ecommerce.dto.BuyProductResponse;
import com.test.jumia.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.test.jumia.ecommerce.entity.ProductUtils.buildBuyProductResponse;

import static com.test.jumia.ecommerce.entity.ProductUtils.buildBuyProductResponseList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @Test
    void shouldReturnAListOfProducts() {
        List<BuyProductResponse> buyProductResponseList = buildBuyProductResponseList();
        when(productService.listAllProducts()).thenReturn(buyProductResponseList);
        ResponseEntity response = productController.listProducts();
        List<BuyProductResponse> responseBody = (List<BuyProductResponse>) response.getBody();
        for(int i = 0; i < responseBody.size(); i++) {
            assertEquals(buyProductResponseList.get(i).getSku(), responseBody.get(0).getSku());
            assertEquals(buyProductResponseList.get(i).getName(), responseBody.get(0).getName());
            assertEquals(buyProductResponseList.get(i).getCountry(), responseBody.get(0).getCountry());
            assertEquals(buyProductResponseList.get(i).getStockChange(), responseBody.get(0).getStockChange());
        }
    }

    @Test
    void shouldGetAProduct() {
        BuyProductResponse buyProductResponse = buildBuyProductResponse(1);
        when(productService.getProduct(anyString(), anyString())).thenReturn(buyProductResponse);
        ResponseEntity response = productController.getProduct(anyString(), anyString());
        BuyProductResponse responseBody = (BuyProductResponse) response.getBody();

        assertEquals(buyProductResponse.getSku(), responseBody.getSku());
        assertEquals(buyProductResponse.getName(), responseBody.getName());
        assertEquals(buyProductResponse.getCountry(), responseBody.getCountry());
        assertEquals(buyProductResponse.getStockChange(), responseBody.getStockChange());
    }

    @Test
    void shouldNotFoundAProduct() {
        when(productService.getProduct(anyString(), anyString())).thenReturn(null);
        ResponseEntity response = productController.getProduct(anyString(), anyString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
