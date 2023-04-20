package by.ivankov.delivery.repository;

import by.ivankov.delivery.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteById(Long id);
    Optional<Cart> findById(Long id);
}
