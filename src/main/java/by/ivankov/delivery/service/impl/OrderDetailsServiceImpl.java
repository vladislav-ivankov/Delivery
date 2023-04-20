package by.ivankov.delivery.service.impl;

import by.ivankov.delivery.model.OrderDetails;
import by.ivankov.delivery.repository.OrderDetailsRepository;
import by.ivankov.delivery.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public OrderDetails getOrderDetailsById(Long id) {
        return orderDetailsRepository.getOrderDetailsById(id);
    }

    @Override
    public void save(List<OrderDetails> orderDetails) {
        orderDetailsRepository.saveAll(orderDetails);
    }
}
