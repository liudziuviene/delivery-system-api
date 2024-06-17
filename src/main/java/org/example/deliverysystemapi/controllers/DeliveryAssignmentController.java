package org.example.deliverysystemapi.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliverysystemapi.converters.CourierConverter;
import org.example.deliverysystemapi.converters.DeliveryAssignmentConverter;
import org.example.deliverysystemapi.converters.OrderConverter;
import org.example.deliverysystemapi.dto.*;
import org.example.deliverysystemapi.entities.Courier;
import org.example.deliverysystemapi.entities.DeliveryAssignment;
import org.example.deliverysystemapi.entities.Order;
import org.example.deliverysystemapi.exceptions.*;
import org.example.deliverysystemapi.services.DeliveryAssignmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveryAssignments")
public class DeliveryAssignmentController {

    private final String NO_DELIVERY_ASSIGNMENT_FOUND = "No delivery assignment found with ID {}";

    private final DeliveryAssignmentService deliveryAssignmentService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<List<CreateDeliveryAssignmentResponse>> getAllDeliveryAssignments() {
        log.debug("Get all delivery assignments");
        List<DeliveryAssignment> deliveryAssignments = deliveryAssignmentService.getAllDeliveryAssignments();
        if (deliveryAssignments.isEmpty()) {
            log.info("No delivery assignments found");
            return ResponseEntity.notFound().build();
        }

        log.info("Found {} delivery assignments", deliveryAssignments.size());
        ResponseEntity<List<CreateDeliveryAssignmentResponse>> response = ResponseEntity.ok(
                DeliveryAssignmentConverter.convertDeliveryAssignmentsToCreateDeliveryAssignmentResponses(
                        deliveryAssignments));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'COURIER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getDeliveryAssignmentById(@PathVariable Long id) {
        log.debug("Get delivery assignment by ID: {}", id);
        DeliveryAssignment deliveryAssignment = deliveryAssignmentService.getDeliveryAssignmentById(id);
        if (deliveryAssignment == null) {
            log.info(NO_DELIVERY_ASSIGNMENT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("delivery assignment", "ID", id.toString()));
        }

        log.info("Found delivery assignment with ID: {}", id);
        ResponseEntity<CreateDeliveryAssignmentResponse> response = ResponseEntity.ok(
                DeliveryAssignmentConverter.convertDeliveryAssignmentToCreateDeliveryAssignmentResponse(deliveryAssignment));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @GetMapping("/{id}/courier")
    public ResponseEntity<?> getCourierByDeliveryAssignmentId(@PathVariable Long id) {
        log.debug("Get courier by delivery assignment ID: {}", id);
        Courier courier = deliveryAssignmentService.getCourierByDeliveryAssignmentId(id);
        if (courier == null) {
            log.info(NO_DELIVERY_ASSIGNMENT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("delivery assignment", "ID", id.toString()));
        }

        log.info("Found courier for delivery assignment ID: {}", id);
        ResponseEntity<CreateCourierResponse> response = ResponseEntity.ok(
                CourierConverter.convertCourierToCreateCourierResponse(courier));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'COURIER')")
    @GetMapping("/{id}/order")
    public ResponseEntity<?> getOrderByDeliveryAssignmentId(@PathVariable Long id) {
        log.debug("Get order by delivery assignment ID: {}", id);
        Order order = deliveryAssignmentService.getOrderByDeliveryAssignmentId(id);
        if (order == null) {
            log.info(NO_DELIVERY_ASSIGNMENT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("delivery assignment", "ID", id.toString()));
        }

        log.info("Found order for delivery assignment ID: {}", id);
        ResponseEntity<CreateOrderResponse> response = ResponseEntity.ok(
                OrderConverter.convertOrderToCreateOrderResponse(order));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'COURIER')")
    @GetMapping("/courier/{courierId}")
    public ResponseEntity<?> getAllDeliveryAssignmentsByCourierId(@PathVariable Long courierId) {
        log.debug("Get all delivery assignments by courier ID: {}", courierId);
        try {
            List<DeliveryAssignment> deliveryAssignments = deliveryAssignmentService.getAllDeliveryAssignmentsByCourierId(courierId);

            if (deliveryAssignments.isEmpty()) {
                log.info("No delivery assignments found for courier ID {}", courierId);
                return ResponseEntity.notFound().build();
            }

            log.info("Found {} delivery assignments for courier ID {}", deliveryAssignments.size(), courierId);
            ResponseEntity<List<CreateDeliveryAssignmentResponse>> response = ResponseEntity.ok(
                    DeliveryAssignmentConverter.convertDeliveryAssignmentsToCreateDeliveryAssignmentResponses(deliveryAssignments));
            log.debug("Response: {}", response);
            return response;
        } catch (CourierNotFoundException ex) {
            log.info("Courier not found with ID: {}", courierId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CourierNotFoundErrorResponse(ex.getCourierId()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<?> addDeliveryAssignment(@Valid @RequestBody CreateDeliveryAssignmentRequest
                                                           createDeliveryAssignmentRequest, BindingResult bindingResult) {
        log.debug("Add delivery assignment {}", createDeliveryAssignmentRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        try {
            DeliveryAssignment deliveryAssignment = deliveryAssignmentService.addDeliveryAssignment(
                    createDeliveryAssignmentRequest);
            ResponseEntity<CreateDeliveryAssignmentResponse> response = ResponseEntity.status(HttpStatus.CREATED)
                    .body(DeliveryAssignmentConverter.convertDeliveryAssignmentToCreateDeliveryAssignmentResponse(
                            deliveryAssignment));
            log.debug("Response: {}", response);
            return response;
        } catch (DuplicateDeliveryAssignmentException ex) {
            log.info("Delivery assignment for order with ID {} already exists", createDeliveryAssignmentRequest.getOrderId());
            return ResponseEntity.status(HttpStatus.CONFLICT).
                    body(new DuplicateDeliveryAssignmentErrorResponse(ex.getOrderId()));
        } catch (OrderNotFoundException ex) {
            log.info("Order not found with ID {}", createDeliveryAssignmentRequest.getOrderId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new OrderNotFoundErrorResponse(ex.getOrderId()));
        } catch (CourierNotFoundException ex) {
            log.info("Courier not found with ID {}", createDeliveryAssignmentRequest.getCourierId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CourierNotFoundErrorResponse(ex.getCourierId()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDeliveryAssignment(@Valid @RequestBody UpdateDeliveryAssignmentRequest updateDeliveryAssignmentRequest,
                                                      @PathVariable Long id, BindingResult bindingResult) {
        log.debug("Update delivery assignment {}", updateDeliveryAssignmentRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        try {
            DeliveryAssignment updatedDeliveryAssignment = deliveryAssignmentService.updateDeliveryAssignment(
                    id, updateDeliveryAssignmentRequest);

            if (updatedDeliveryAssignment == null) {
                log.info(NO_DELIVERY_ASSIGNMENT_FOUND, id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new NotFoundErrorResponse("delivery assignment", "ID", id.toString()));
            }

            log.debug("Updated delivery assignment with ID {} with data {}", id, updatedDeliveryAssignment);
            ResponseEntity<CreateDeliveryAssignmentResponse> response = ResponseEntity.status(HttpStatus.OK)
                    .body(DeliveryAssignmentConverter.convertDeliveryAssignmentToCreateDeliveryAssignmentResponse(
                            updatedDeliveryAssignment));
            log.debug("Response: {}", response);
            return response;
        } catch (DuplicateDeliveryAssignmentException ex) {
            log.info("Delivery assignment for order with ID {} already exists", updateDeliveryAssignmentRequest.getOrderId());
            return ResponseEntity.status(HttpStatus.CONFLICT).
                    body(new DuplicateDeliveryAssignmentErrorResponse(ex.getOrderId()));
        } catch (OrderNotFoundException ex) {
            log.info("Order not found with ID {}", updateDeliveryAssignmentRequest.getOrderId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new OrderNotFoundErrorResponse(ex.getOrderId()));
        } catch (CourierNotFoundException ex) {
            log.info("Courier not found with ID {}", updateDeliveryAssignmentRequest.getCourierId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CourierNotFoundErrorResponse(ex.getCourierId()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeliveryAssignmentById(@PathVariable Long id) {
        log.debug("Delete delivery assignment {}", id);
        DeliveryAssignment deliveryAssignment = deliveryAssignmentService.getDeliveryAssignmentById(id);
        if (deliveryAssignment == null) {
            log.info(NO_DELIVERY_ASSIGNMENT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("delivery assignment", "ID", id.toString()));
        }

        deliveryAssignmentService.deleteDeliveryAssignmentById(id);
        log.info("Deleted delivery assignment with ID {}", id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @GetMapping("/filter")
    public ResponseEntity<?> filterDeliveryAssignments(
            @RequestParam(required = false) Long courierId,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime date) {
        log.debug("Filtering delivery assignments with criteria - courierId {}, orderId {}, date {}", courierId, orderId,
                date);

        List<DeliveryAssignment> deliveryAssignments = deliveryAssignmentService.filterDeliveryAssignments(courierId, orderId,
                date);
        if (deliveryAssignments == null || deliveryAssignments.isEmpty()) {
            log.info("No delivery assignment found for given criteria");
            return ResponseEntity.noContent().build();
        }
        ResponseEntity<List<CreateDeliveryAssignmentResponse>> response = ResponseEntity.ok(
                DeliveryAssignmentConverter.convertDeliveryAssignmentsToCreateDeliveryAssignmentResponses(deliveryAssignments));
        log.debug("Response: {}", response);
        return response;
    }
}
