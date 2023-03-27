package by.ivankov.delivery.service;

import by.ivankov.delivery.exception.ServiceException;
import by.ivankov.delivery.model.Cart;

public interface CartService {
    void save(Cart cart);

    void addProductToCart(String username, Long productId) throws ServiceException;

}
