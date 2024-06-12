package org.example.deliverysystemapi.repositories;

import org.example.deliverysystemapi.entities.DeliveryAssignment;
import org.example.deliverysystemapi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment, Long> {

    DeliveryAssignment findByOrder(Order order);

    List<DeliveryAssignment> findByCourierId(Long courierId);
}
