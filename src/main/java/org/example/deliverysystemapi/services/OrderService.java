package org.example.deliverysystemapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliverysystemapi.dto.CreateOrderRequest;
import org.example.deliverysystemapi.dto.UpdateOrderRequest;
import org.example.deliverysystemapi.entities.Customer;
import org.example.deliverysystemapi.entities.Order;
import org.example.deliverysystemapi.exceptions.CustomerNotFoundException;
import org.example.deliverysystemapi.repositories.CustomerRepository;
import org.example.deliverysystemapi.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Order with ID {} not found", id);
        }
        return order;
    }

    public List<Order> getAllOrdersByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            log.warn("Customer with ID {} not found", customerId);
            throw new CustomerNotFoundException(customerId);
        }
        return orderRepository.findByCustomerId(customerId);
    }

    public Order addOrder(CreateOrderRequest createOrderRequest) {
        Order order = new Order();
        order.setPickupAddress(createOrderRequest.getPickupAddress());
        order.setDeliveryAddress(createOrderRequest.getDeliveryAddress());
        order.setOrderDate(createOrderRequest.getOrderDate());
        order.setDeliveryDate(createOrderRequest.getDeliveryDate());
        order.setStatus(createOrderRequest.getStatus());
        Customer customer = customerRepository.findById(createOrderRequest.getCustomerId()).orElse(null);
        if (customer == null) {
            log.warn("Customer with ID {} not found", createOrderRequest.getCustomerId());
            throw new CustomerNotFoundException(createOrderRequest.getCustomerId());
        }
        order.setCustomer(customer);
        return orderRepository.saveAndFlush(order);
    }

    public Order updateOrder(Long id, UpdateOrderRequest updateOrderRequest) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Order with ID {} not found", id);
            return null;
        }
        if (updateOrderRequest.getPickupAddress() != null) {
            order.setPickupAddress(updateOrderRequest.getPickupAddress());
        }
        if (updateOrderRequest.getDeliveryAddress() != null) {
            order.setDeliveryAddress(updateOrderRequest.getDeliveryAddress());
        }
        if (updateOrderRequest.getOrderDate() != null) {
            order.setOrderDate(updateOrderRequest.getOrderDate());
        }
        if (updateOrderRequest.getDeliveryDate() != null) {
            order.setDeliveryDate(updateOrderRequest.getDeliveryDate());
        }
        if (updateOrderRequest.getStatus() != null) {
            order.setStatus(updateOrderRequest.getStatus());
        }
        if (updateOrderRequest.getCustomerId() != null) {
            Customer customer = customerRepository.findById(updateOrderRequest.getCustomerId()).orElse(null);
            if (customer == null) {
                log.warn("Customer with ID {} not found", updateOrderRequest.getCustomerId());
                throw new CustomerNotFoundException(updateOrderRequest.getCustomerId());
            }
            order.setCustomer(customer);
        }
        return orderRepository.saveAndFlush(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> filterOrders(Long customerId, String pickupAddress, String deliveryAddress, LocalDateTime orderDate,
                                    LocalDateTime deliveryDate, String status) {
        if (customerId == null && pickupAddress == null && deliveryAddress == null && orderDate == null && deliveryDate == null && status == null) {
            return null;
        }
        return orderRepository.findByCustomerIdAndPickupAddressAndDeliveryAddressAndOrderDateAndDeliveryDateAndStatus(
                customerId, pickupAddress, deliveryAddress, orderDate, deliveryDate, status);
    }
}
