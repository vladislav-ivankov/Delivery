package by.ivankov.delivery.service;

import by.ivankov.delivery.model.Order;

public interface OrderService {
    Order save(Order order);

    Order getOrderById(Long id);
}
