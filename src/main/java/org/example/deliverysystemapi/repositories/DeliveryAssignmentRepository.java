package org.example.deliverysystemapi.repositories;

import org.example.deliverysystemapi.entities.DeliveryAssignment;
import org.example.deliverysystemapi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment, Long> {

    DeliveryAssignment findByOrder(Order order);

    List<DeliveryAssignment> findByCourierId(Long courierId);

    @Query("SELECT d FROM DeliveryAssignment d WHERE " +
            "(:courierId IS NULL OR d.courier.id = :courierId) AND " +
            "(:orderId IS NULL OR d.order.id = :orderId) AND " +
            "(:date IS NULL OR d.date = :date)")
    List<DeliveryAssignment> findByCourierIdAndOrderIdAndDate(@Param("courierId") Long courierId,
                                                              @Param("orderId") Long orderId,
                                                              @Param("date") LocalDateTime date);
}
