package by.ivankov.delivery.service.impl;

import by.ivankov.delivery.model.Product;
import by.ivankov.delivery.repository.ProductRepository;
import by.ivankov.delivery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public void save(Product product) {
        productRepository.save(product);
    }
}
