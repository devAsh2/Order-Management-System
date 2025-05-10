package com.codeAsh.orderService.repository;

import com.codeAsh.orderService.models.orders;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<orders, Long> {
    Optional<orders> findByOrderNumber(String orderNumber);
}
