package com.test.jumia.ecommerce.service;

import com.test.jumia.ecommerce.clients.ProductCatalogClient;
import com.test.jumia.ecommerce.dto.BuyProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.test.jumia.ecommerce.entity.ProductUtils.buildBuyProductResponse;
import static com.test.jumia.ecommerce.entity.ProductUtils.buildBuyProductResponseList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductCacheServiceTest {

    @InjectMocks
    ProductCacheService productCacheService;

    @Mock
    ProductCatalogClient productCatalogClient;

    @Test
    void cacheTheListAllProductsCall() {
        List<BuyProductResponse> productResponses = buildBuyProductResponseList();
        when(productCatalogClient.listAllProducts()).thenReturn(productResponses);

        productCacheService.listProducts();

        verify(productCatalogClient, times(1)).listAllProducts();
    }

    @Test
    void cacheTheSearchProductCall() {
        List<BuyProductResponse> productResponses = buildBuyProductResponseList();
        when(productCatalogClient.search(anyString())).thenReturn(productResponses);

        productCacheService.search(anyString());

        verify(productCatalogClient, times(1)).search(anyString());
    }
}
