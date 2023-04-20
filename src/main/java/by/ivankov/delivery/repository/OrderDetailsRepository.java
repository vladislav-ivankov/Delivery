package by.ivankov.delivery.repository;

import by.ivankov.delivery.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    OrderDetails getOrderDetailsById(Long id);
}
