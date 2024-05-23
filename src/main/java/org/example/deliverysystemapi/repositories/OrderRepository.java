package org.example.deliverysystemapi.repositories;

import org.example.deliverysystemapi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
