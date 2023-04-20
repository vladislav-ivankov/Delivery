package by.ivankov.delivery.service;

import by.ivankov.delivery.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void save(Category category);
    List<Category> findAll();
    Optional<Category> findById(Long id);
}
