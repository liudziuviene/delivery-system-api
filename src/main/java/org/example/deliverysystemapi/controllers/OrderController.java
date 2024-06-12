package org.example.deliverysystemapi.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliverysystemapi.converters.OrderConverter;
import org.example.deliverysystemapi.dto.CreateOrderRequest;
import org.example.deliverysystemapi.dto.CreateOrderResponse;
import org.example.deliverysystemapi.dto.UpdateOrderRequest;
import org.example.deliverysystemapi.entities.Order;
import org.example.deliverysystemapi.exceptions.*;
import org.example.deliverysystemapi.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private static final String NO_ORDER_FOUND = "No order found with ID {}";
    private static final String RESPONSE = "Response {}";

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<CreateOrderResponse>> getAllOrders() {
        log.info("Get all orders");
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            log.info("No orders found");
            return ResponseEntity.noContent().build();
        }

        log.info("Found {} orders", orders.size());
        ResponseEntity<List<CreateOrderResponse>> response = ResponseEntity.ok(
                OrderConverter.convertOrdersToCreateOrderResponses(orders));
        log.debug(RESPONSE, response);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        log.info("Get order by id {}", id);
        Order order = orderService.getOrderById(id);
        if (order == null) {
            log.info(NO_ORDER_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("order", "ID", id.toString()));
        }

        log.info("Found order with ID {}", id);
        ResponseEntity<CreateOrderResponse> response = ResponseEntity.ok()
                .body(OrderConverter.convertOrderToCreateOrderResponse(order));
        log.debug(RESPONSE, response);
        return response;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getAllOrdersByCustomerId(@PathVariable Long customerId) {
        log.debug("Get all orders by customer ID: {}", customerId);
        try {
            List<Order> orders = orderService.getAllOrdersByCustomerId(customerId);

            if (orders.isEmpty()) {
                log.info("No orders found for customer ID {}", customerId);
                return ResponseEntity.notFound().build();
            }

            log.info("Found {} orders for customer ID {}", orders.size(), customerId);
            ResponseEntity<List<CreateOrderResponse>> response = ResponseEntity.ok(
                    OrderConverter.convertOrdersToCreateOrderResponses(orders));
            log.debug("Response: {}", response);
            return response;
        } catch (CustomerNotFoundException ex) {
            log.info("Customer not found with ID: {}", customerId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomerNotFoundErrorResponse(ex.getCustomerId()));
        }
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest,
                                      BindingResult bindingResult) {
        log.debug("Add order {}", createOrderRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        try {
            Order order = orderService.addOrder(createOrderRequest);
            ResponseEntity<CreateOrderResponse> response = ResponseEntity.status(HttpStatus.CREATED)
                    .body(OrderConverter.convertOrderToCreateOrderResponse(order));
            log.debug(RESPONSE, response);
            return response;
        } catch (CustomerNotFoundException ex) {
            log.info("Customer with ID {} not found", createOrderRequest.getCustomerId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomerNotFoundErrorResponse(ex.getCustomerId()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @RequestBody UpdateOrderRequest updateOrderRequest,
                                         @PathVariable Long id, BindingResult bindingResult) {
        log.debug("Update order {}", updateOrderRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        try {
            Order updatedOrder = orderService.updateOrder(id, updateOrderRequest);
            if (updatedOrder == null) {
                log.info(NO_ORDER_FOUND, id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new NotFoundErrorResponse("order", "ID", id.toString()));
            }

            log.debug("Updated order with ID {} with data {}", id, updatedOrder);
            ResponseEntity<CreateOrderResponse> response = ResponseEntity.status(HttpStatus.OK)
                    .body(OrderConverter.convertOrderToCreateOrderResponse(updatedOrder));
            log.debug(RESPONSE, response);
            return response;
        } catch (CustomerNotFoundException ex) {
            log.info("Customer by ID {} not found", updateOrderRequest.getCustomerId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomerNotFoundErrorResponse(ex.getCustomerId()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long id) {
        log.debug("Delete order by ID {}", id);
        Order order = orderService.getOrderById(id);
        if (order == null) {
            log.info(NO_ORDER_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("order", "ID", id.toString()));
        }

        orderService.deleteOrderById(id);
        log.info("Deleted order with ID {}", id);
        return ResponseEntity.noContent().build();
    }
}
