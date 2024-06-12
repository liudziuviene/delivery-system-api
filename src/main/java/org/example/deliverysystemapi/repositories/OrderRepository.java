package org.example.deliverysystemapi.repositories;

import org.example.deliverysystemapi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    @Query("SELECT o FROM Order o WHERE " +
            "(:customerId IS NULL OR o.customer.id = :customerId) AND " +
            "(:pickupAddress IS NULL OR o.pickupAddress = :pickupAddress) AND " +
            "(:deliveryAddress IS NULL OR o.deliveryAddress = :deliveryAddress) AND " +
            "(:orderDate IS NULL OR o.orderDate = :orderDate) AND " +
            "(:deliveryDate IS NULL OR o.deliveryDate = :deliveryDate) AND " +
            "(:status IS NULL OR o.status = :status)")
    List<Order> findByCustomerIdAndPickupAddressAndDeliveryAddressAndOrderDateAndDeliveryDateAndStatus(
            @Param("customerId") Long customerId,
            @Param("pickupAddress") String pickupAddress,
            @Param("deliveryAddress") String deliveryAddress,
            @Param("orderDate") LocalDateTime orderDate,
            @Param("deliveryDate") LocalDateTime deliveryDate,
            @Param("status") String status);
}
