package by.ivankov.delivery.service.impl;

import by.ivankov.delivery.exception.ServiceException;
import by.ivankov.delivery.model.Cart;
import by.ivankov.delivery.model.Product;
import by.ivankov.delivery.model.User;
import by.ivankov.delivery.repository.CartRepository;
import by.ivankov.delivery.repository.ProductRepository;
import by.ivankov.delivery.repository.UserRepository;
import by.ivankov.delivery.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartServiceImpl(ProductRepository productRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void addProductToCart(String username, Long productId) throws ServiceException {
        User user = userRepository.findUserByUsername(username);
        Cart cart = user.getCart();
        Product product = productRepository.getProductById(productId);
        if(product == null) {
            throw new ServiceException("Product not found");
        }
        if(cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setProducts(new ArrayList<>());
        }
        List<Product> products = cart.getProducts();
        products.add(product);
        cart.setProducts(products);
        cartRepository.save(cart);
    }

}
