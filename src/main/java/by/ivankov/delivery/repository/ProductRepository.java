package by.ivankov.delivery.repository;

import by.ivankov.delivery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    Product getProductById(Long id);
    void deleteById(Long id);
}
