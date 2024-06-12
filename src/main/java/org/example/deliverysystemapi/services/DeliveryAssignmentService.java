package org.example.deliverysystemapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliverysystemapi.dto.CreateDeliveryAssignmentRequest;
import org.example.deliverysystemapi.dto.UpdateDeliveryAssignmentRequest;
import org.example.deliverysystemapi.entities.Courier;
import org.example.deliverysystemapi.entities.DeliveryAssignment;
import org.example.deliverysystemapi.entities.Order;
import org.example.deliverysystemapi.exceptions.CourierNotFoundException;
import org.example.deliverysystemapi.exceptions.DuplicateDeliveryAssignmentException;
import org.example.deliverysystemapi.exceptions.OrderNotFoundException;
import org.example.deliverysystemapi.repositories.CourierRepository;
import org.example.deliverysystemapi.repositories.DeliveryAssignmentRepository;
import org.example.deliverysystemapi.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryAssignmentService {

    private final String NO_DELIVERY_ASSIGNMENT_FOUND = "No delivery assignment found with ID {}";

    private final DeliveryAssignmentRepository deliveryAssignmentRepository;
    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;

    public List<DeliveryAssignment> getAllDeliveryAssignments() {
        return deliveryAssignmentRepository.findAll();
    }

    public DeliveryAssignment getDeliveryAssignmentById(Long id) {
        DeliveryAssignment deliveryAssignment = deliveryAssignmentRepository.findById(id).orElse(null);
        if (deliveryAssignment == null) {
            log.warn(NO_DELIVERY_ASSIGNMENT_FOUND, id);
        }
        return deliveryAssignment;
    }

    public Courier getCourierByDeliveryAssignmentId(Long id) {
        DeliveryAssignment deliveryAssignment = getDeliveryAssignmentById(id);
        if (deliveryAssignment == null) {
            log.warn(NO_DELIVERY_ASSIGNMENT_FOUND, id);
            return null;
        }
        return deliveryAssignment.getCourier();
    }

    public Order getOrderByDeliveryAssignmentId(Long id) {
        DeliveryAssignment deliveryAssignment = getDeliveryAssignmentById(id);
        if (deliveryAssignment == null) {
            log.warn(NO_DELIVERY_ASSIGNMENT_FOUND, id);
            return null;
        }
        return deliveryAssignment.getOrder();
    }

    public List<DeliveryAssignment> getAllDeliveryAssignmentsByCourierId(Long courierId) {
        Courier courier = courierRepository.findById(courierId).orElse(null);
        if (courier == null) {
            log.warn("Courier with ID {} not found", courierId);
            throw new CourierNotFoundException(courierId);
        }
        return deliveryAssignmentRepository.findByCourierId(courierId);
    }

    public DeliveryAssignment addDeliveryAssignment(CreateDeliveryAssignmentRequest createDeliveryAssignmentRequest) {
        DeliveryAssignment deliveryAssignment = new DeliveryAssignment();
        deliveryAssignment.setDate(createDeliveryAssignmentRequest.getDate());

        Order order = orderRepository.findById(createDeliveryAssignmentRequest.getOrderId()).orElse(null);
        if (order == null) {
            log.warn("Order with ID {} not found", createDeliveryAssignmentRequest.getOrderId());
            throw new OrderNotFoundException(createDeliveryAssignmentRequest.getOrderId());
        }

        DeliveryAssignment existingAssignment = deliveryAssignmentRepository.findByOrder(order);
        if (existingAssignment != null) {
            log.warn("Delivery assignment for order with ID {} already exists", createDeliveryAssignmentRequest.getOrderId());
            throw new DuplicateDeliveryAssignmentException(createDeliveryAssignmentRequest.getOrderId());
        }
        deliveryAssignment.setOrder(order);

        Courier courier = courierRepository.findById(createDeliveryAssignmentRequest.getCourierId()).orElse(null);
        if (courier == null) {
            log.warn("Courier with ID {} not found", createDeliveryAssignmentRequest.getCourierId());
            throw new CourierNotFoundException(createDeliveryAssignmentRequest.getCourierId());
        }
        deliveryAssignment.setCourier(courier);

        return deliveryAssignmentRepository.saveAndFlush(deliveryAssignment);
    }

    public DeliveryAssignment updateDeliveryAssignment(Long id,
                                                       UpdateDeliveryAssignmentRequest updateDeliveryAssignmentRequest) {
        DeliveryAssignment deliveryAssignment = deliveryAssignmentRepository.findById(id).orElse(null);
        if (deliveryAssignment == null) {
            log.warn(NO_DELIVERY_ASSIGNMENT_FOUND, id);
            return null;
        }

        if (updateDeliveryAssignmentRequest.getDate() != null) {
            deliveryAssignment.setDate(updateDeliveryAssignmentRequest.getDate());
        }
        if (updateDeliveryAssignmentRequest.getOrderId() != null) {
            Order order = orderRepository.findById(updateDeliveryAssignmentRequest.getOrderId()).orElse(null);
            if (order == null) {
                log.warn("Order with ID {} not found", updateDeliveryAssignmentRequest.getOrderId());
                throw new OrderNotFoundException(updateDeliveryAssignmentRequest.getOrderId());
            }

            DeliveryAssignment existingAssignment = deliveryAssignmentRepository.findByOrder(order);
            if (existingAssignment != null) {
                log.warn("Delivery assignment for order with ID {} already exists", updateDeliveryAssignmentRequest.getOrderId());
                throw new DuplicateDeliveryAssignmentException(updateDeliveryAssignmentRequest.getOrderId());
            }
            deliveryAssignment.setOrder(order);
        }
        if (updateDeliveryAssignmentRequest.getCourierId() != null) {
            Courier courier = courierRepository.findById(updateDeliveryAssignmentRequest.getCourierId()).orElse(null);
            if (courier == null) {
                log.warn("Courier with ID {} not found", updateDeliveryAssignmentRequest.getCourierId());
                throw new CourierNotFoundException(updateDeliveryAssignmentRequest.getCourierId());
            }
            deliveryAssignment.setCourier(courier);
        }
        return deliveryAssignmentRepository.saveAndFlush(deliveryAssignment);
    }

    public void deleteDeliveryAssignmentById(Long id) {
        deliveryAssignmentRepository.deleteById(id);
    }
}