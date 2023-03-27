package by.ivankov.delivery.repository;

import by.ivankov.delivery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    Product getProductById(Long id);
    void deleteById(Long id);

    List<Product> findByProductNameContainingIgnoreCase(String query);

    @Override
    Optional<Product> findById(Long id);
}
