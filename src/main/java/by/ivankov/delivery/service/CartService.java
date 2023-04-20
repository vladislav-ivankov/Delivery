package by.ivankov.delivery.service;

import by.ivankov.delivery.exception.ServiceException;
import by.ivankov.delivery.model.Cart;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;

public interface CartService {
    void save(Cart cart);
    void addProductToCart(String username, Long productId) throws ServiceException;
    Cart cartPage(@AuthenticationPrincipal UserDetails userDetails);
    void removeProduct(Long cartId, Long productId);
    BigDecimal getSubtotalPrice(Cart cart);
}
