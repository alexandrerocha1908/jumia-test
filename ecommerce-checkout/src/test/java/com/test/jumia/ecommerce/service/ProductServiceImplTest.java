package com.test.jumia.ecommerce.service;

import com.test.jumia.ecommerce.clients.ProductCatalogClient;
import com.test.jumia.ecommerce.dto.BuyProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.test.jumia.ecommerce.entity.ProductUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductCatalogClient productCatalogClient;

    @Mock
    ProductCacheService productCacheService;

    @Test
    public void shouldReturnListOfProducts() {
        List<BuyProductResponse> buyProductResponses = buildBuyProductResponseList();
        when(productCacheService.listProducts()).thenReturn(buyProductResponses);

        productService.listAllProducts();

        verify(productCacheService, times(1)).listProducts();
    }

    @Test
    public void shouldReturnAProduct() {
        BuyProductResponse buyProductResponse = buildBuyProductResponse(1);
        ArgumentCaptor<String> skuCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> countryCapture = ArgumentCaptor.forClass(String.class);
        when(productCatalogClient.getProduct(anyString(), anyString())).thenReturn(buyProductResponse);

        productService.getProduct("sku", "country");

        verify(productCatalogClient, times(1)).getProduct(skuCapture.capture(), countryCapture.capture());
        assertEquals(buyProductResponse.getSku(), skuCapture.getValue());
        assertEquals(buyProductResponse.getCountry(), countryCapture.getValue());
    }

    @Test
    public void shouldReturnTrueWhenCheckAvailability() {
        when(productCatalogClient.checkAvailability(any())).thenReturn(true);

        productService.checkAvailability(buildBuyProductRequest());

        verify(productCatalogClient, times(1)).checkAvailability(any());
    }

    @Test
    public void shouldReturnFalseCheckAvailability() {
        when(productCatalogClient.checkAvailability(any())).thenReturn(false);

        productService.checkAvailability(buildBuyProductRequest());

        verify(productCatalogClient, times(1)).checkAvailability(any());
    }
}

