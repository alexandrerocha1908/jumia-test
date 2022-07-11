package com.test.jumia.ecommerce.service;

import com.test.jumia.ecommerce.dto.BuyProductRequest;
import com.test.jumia.ecommerce.dto.BuyProductResponse;
import com.test.jumia.ecommerce.entity.Product;
import com.test.jumia.ecommerce.exception.OutOfStockException;
import com.test.jumia.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void listProducts() {
        List<Product> products = buildProductList();
        when(productRepository.findAll()).thenReturn(products);
        List<BuyProductResponse> buyProductResponses = productService.listAllProducts();

        for(int i = 0; i < buyProductResponses.size(); i++) {
            assertEquals(products.get(0).getStockChange(), buyProductResponses.get(0).getStockChange().toString());
            assertEquals(products.get(0).getSku(), buyProductResponses.get(0).getSku());
            assertEquals(products.get(0).getName(), buyProductResponses.get(0).getName());
            assertEquals(products.get(0).getCountry(), buyProductResponses.get(0).getCountry());
        }
    }

    @Test
    void getProduct() {
        Product product = buildProduct("1");
        when(productRepository.findProductBySkuAndCountry(anyString(), anyString())).thenReturn(product);
        BuyProductResponse buyProductResponse = productService.getProduct(anyString(), anyString());

        assertEquals(product.getStockChange(), buyProductResponse.getStockChange().toString());
        assertEquals(product.getSku(), buyProductResponse.getSku());
        assertEquals(product.getName(), buyProductResponse.getName());
        assertEquals(product.getCountry(), buyProductResponse.getCountry());
    }

    @Test
    void checkAvailabilityReturnTrue() {
        Product product = buildProduct("1");
        when(productRepository.findProductBySkuAndCountry(anyString(), anyString())).thenReturn(product);
        Boolean isAvailable = productService.checkAvailability(buildProductRequest(1));

        assertTrue(isAvailable);
    }

    @Test
    void checkAvailabilityReturnFalse() {
        Product product = buildProduct("0");
        when(productRepository.findProductBySkuAndCountry(anyString(), anyString())).thenReturn(product);
        Boolean isAvailable = productService.checkAvailability(buildProductRequest(1));

        assertFalse(isAvailable);
    }

    @Test
    void buyingAProductOutOfStock() {
        BuyProductRequest buyProductRequest = buildProductRequest(1);
        Product product = buildProduct("0");
        when(productRepository.findProductBySkuAndCountry(anyString(), anyString())).thenReturn(product);
        assertThrows(OutOfStockException.class, () -> productService.buyProduct(buyProductRequest));
    }

}
