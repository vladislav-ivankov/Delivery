package by.ivankov.delivery.service;

import by.ivankov.delivery.model.Product;

import java.util.List;

public interface ProductService {
    void save(Product product);
    Product getProductById(Long id);

    List<Product> searchProducts(String query);
}
