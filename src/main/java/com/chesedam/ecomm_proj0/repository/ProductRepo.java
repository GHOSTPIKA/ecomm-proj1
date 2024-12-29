package com.chesedam.ecomm_proj0.repository;

import com.chesedam.ecomm_proj0.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE "+
    "LOWER(p.name) LIKE LOWER(CONCAT('%',:Keyword, '%')) OR " +
    "LOWER(p.brand) LIKE LOWER(CONCAT('%',:Keyword, '%')) OR "+
    "LOWER(p.category) LIKE LOWER(CONCAT('%',:Keyword, '%'))")
    List<Product> searchProducts(String Keyword);
}
