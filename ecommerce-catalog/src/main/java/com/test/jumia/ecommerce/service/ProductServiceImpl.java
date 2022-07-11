package com.test.jumia.ecommerce.service;

import com.test.jumia.ecommerce.dto.BuyProductRequest;
import com.test.jumia.ecommerce.dto.BuyProductResponse;
import com.test.jumia.ecommerce.entity.Product;
import com.test.jumia.ecommerce.exception.OutOfStockException;
import com.test.jumia.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<BuyProductResponse> listAllProducts() {
        List<BuyProductResponse> result = new ArrayList<>();
        List<Product> products = productRepository.findAll();

        products.forEach(product -> {
            result.add(convertProductToBuyProductResponse(product));
        });

        return result;
    }

    @Override
    public List<BuyProductResponse> search(String productName) {
        List<BuyProductResponse> result = new ArrayList<>();
        List<Product> productList = productRepository.search(productName);

        productList.forEach(product -> {
            result.add(convertProductToBuyProductResponse(product));
        });

        return result;
    }

    @Override
    public BuyProductResponse getProduct(String sku, String country) {
        Product productFound = productRepository.findProductBySkuAndCountry(sku, country);
        if (productFound != null)
            return convertProductToBuyProductResponse(productFound);
        else
            return null;
    }

    @Override
    public void buyProduct(BuyProductRequest buyProductDTO) {
        Product productFound = productRepository.findProductBySkuAndCountry(buyProductDTO.getSku(), buyProductDTO.getCountry());

        Integer stock = Integer.parseInt(productFound.getStockChange());
        if (stock - buyProductDTO.getStockChange() >= 0) {
            productFound.setStockChange(String.valueOf(stock - buyProductDTO.getStockChange()));
        } else {
            throw new OutOfStockException("Product out of stock");
        }

        productRepository.save(productFound);
    }

    @Override
    public boolean checkAvailability(BuyProductRequest buyProductDTO) {
        return Integer.parseInt(productRepository.
                findProductBySkuAndCountry(buyProductDTO.getSku(), buyProductDTO.getCountry()).getStockChange())
                >= buyProductDTO.getStockChange();
    }

    private BuyProductResponse convertProductToBuyProductResponse(Product product) {
        return BuyProductResponse.builder()
                .sku(product.getSku())
                .name(product.getName())
                .country(product.getCountry())
                .stockChange(Integer.parseInt(product.getStockChange()))
                .build();
    }
}
