package by.ivankov.delivery.service.impl;

import by.ivankov.delivery.model.Category;
import by.ivankov.delivery.model.Product;
import by.ivankov.delivery.repository.ProductRepository;
import by.ivankov.delivery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    @Override
    public List<Product> searchProducts(String query) {
        List<Product> allProduct = productRepository.findAll();
        List<Product> foundProduct = new ArrayList<>();
        for (Product product : allProduct) {
            if (product.getProductName().toLowerCase().contains(query.toLowerCase())) {
                foundProduct.add(product);
            }
        }
        return foundProduct;
    }

    @Override
    public ResponseEntity<?> updateProduct(Long id, String field, String value) {
        Product product = getProductById(id);
        switch (field) {
            case "productName" -> product.setProductName(value);
            case "price" -> product.setPrice(BigDecimal.valueOf(Integer.valueOf(value)));
            case "description" -> product.setDescription(value);
            case "image" -> product.setImage(value);
            case "compound" -> product.setCompound(value);
            case "storage" -> product.setStorage(value);
            case "manufacturer" -> product.setManufacturer(value);
        }
        save(product);
        return ResponseEntity.ok().build();
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }
}
