package by.ivankov.delivery.service.impl;

import by.ivankov.delivery.exception.ServiceException;
import by.ivankov.delivery.model.Cart;
import by.ivankov.delivery.model.OrderDetails;
import by.ivankov.delivery.model.Product;
import by.ivankov.delivery.model.User;
import by.ivankov.delivery.repository.CartRepository;
import by.ivankov.delivery.repository.ProductRepository;
import by.ivankov.delivery.repository.UserRepository;
import by.ivankov.delivery.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    private static final BigDecimal COST_OF_DELIVERY = BigDecimal.valueOf(3.5);

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
        if (product == null) {
            throw new ServiceException("Product not found");
        }
        if (cart == null) {
            cart = new Cart();
            cart.setProducts(new ArrayList<>());
        }
        List<Product> products = cart.getProducts();
        products.add(product);
        cart.setProducts(products);
        save(cart);
    }

    @Override
    public Cart cartPage(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findUserByUsername(userDetails.getUsername());
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setProducts(new ArrayList<>());
            cart.setUser(user);
            save(cart);
        }

        return cart;
    }

    @Override
    public void removeProduct(Long cartId, Long productId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                List<Product> products = cart.getProducts();
                products.remove(product);
                cart.setProducts(products);
                cartRepository.save(cart);
            }
        }
    }

    @Override
    public BigDecimal getSubtotalPrice(Cart cart) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Product product : cart.getProducts()) {
            OrderDetails orderDetails = new OrderDetails();
            subtotal.add(product.getPrice().multiply(BigDecimal.valueOf(orderDetails.getQuantity())));
        }
        return subtotal;
    }
}
