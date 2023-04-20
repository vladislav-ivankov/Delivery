package by.ivankov.delivery.repository;

import by.ivankov.delivery.model.Category;
import by.ivankov.delivery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category save(Category category);
    List<Category> findAll();
    Optional<Category> findById (Long id);
 }
