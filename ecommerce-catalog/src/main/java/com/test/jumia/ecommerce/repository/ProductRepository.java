package com.test.jumia.ecommerce.repository;

import com.test.jumia.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public Product findProductBySkuAndCountry(String sku, String country);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    public List<Product> search(String productName);

//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query("UPDATE Product SET stockChange = ?1 WHERE sku = ?2 and country = ?3")
//    public void updateProduct(String stockChange, String sku, String country);
}
