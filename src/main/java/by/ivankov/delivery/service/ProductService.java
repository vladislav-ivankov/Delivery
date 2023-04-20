package by.ivankov.delivery.service;

import by.ivankov.delivery.model.Category;
import by.ivankov.delivery.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {
    void save(Product product);
    Product getProductById(Long id);
    List<Product> searchProducts(String query);
    ResponseEntity<?> updateProduct(Long id, String field, String value);
    List<Product> findByCategory(Category category);
}
