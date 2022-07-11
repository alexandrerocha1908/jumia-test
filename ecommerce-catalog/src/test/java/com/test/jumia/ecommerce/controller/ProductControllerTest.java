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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.test.jumia.ecommerce.entity.ProductUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @Test
    void shouldReturnAListOfProducts() {
        List<BuyProductResponse> buyProductResponseList = buildListBuyProductResponse();
        when(productService.listAllProducts()).thenReturn(buyProductResponseList);
        List<BuyProductResponse> responseBody = productController.listProducts();
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
        BuyProductResponse responseBody = productController.getProduct(anyString(), anyString());

        assertEquals(buyProductResponse.getSku(), responseBody.getSku());
        assertEquals(buyProductResponse.getName(), responseBody.getName());
        assertEquals(buyProductResponse.getCountry(), responseBody.getCountry());
        assertEquals(buyProductResponse.getStockChange(), responseBody.getStockChange());
    }

    @Test
    void shouldNotFoundAProduct() {
        when(productService.getProduct(anyString(), anyString())).thenReturn(null);
        BuyProductResponse response = productController.getProduct(anyString(), anyString());
        assertNull(response);
    }

    @Test
    void theProductShouldBeAvailable() {
        when(productService.checkAvailability(any())).thenReturn(true);
        Boolean response = productController.checkAvailability(any());
        assertTrue(response);
    }

    @Test
    void theProductShouldBeNotAvailable() {
        when(productService.checkAvailability(any())).thenReturn(false);
        Boolean response = productController.checkAvailability(any());
        assertFalse(response);
    }

    @Test
    void theProductShouldBeAvailableInStock() throws IOException {
        doNothing().when(productService).buyProduct(any());
        ResponseEntity response = productController.buyProduct(any());
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
    }
}
