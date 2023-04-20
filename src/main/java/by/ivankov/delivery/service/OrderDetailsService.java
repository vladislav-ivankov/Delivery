package by.ivankov.delivery.service;

import by.ivankov.delivery.model.OrderDetails;

import java.util.List;

public interface OrderDetailsService {
    OrderDetails getOrderDetailsById(Long id);
    void save(List<OrderDetails> orderDetails);
}
