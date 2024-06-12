package org.example.deliverysystemapi.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliverysystemapi.converters.CourierConverter;
import org.example.deliverysystemapi.dto.*;
import org.example.deliverysystemapi.entities.Courier;
import org.example.deliverysystemapi.exceptions.NotFoundErrorResponse;
import org.example.deliverysystemapi.exceptions.ValidationErrorResponse;
import org.example.deliverysystemapi.services.CourierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/couriers")
public class CourierController {

    private static final String NO_COURIER_FOUND = "No courier found with ID {}";
    private static final String RESPONSE = "Response {}";

    private final CourierService courierService;

    @GetMapping
    public ResponseEntity<List<SimpleCourierResponse>> getAllCouriers() {
        log.debug("Getting names and surnames of all couriers");
        List<Courier> couriers = courierService.getAllCouriers();
        if (couriers.isEmpty()) {
            log.info("No couriers found");
            return ResponseEntity.noContent().build();
        }
        log.info("Found {} couriers", couriers.size());
        ResponseEntity<List<SimpleCourierResponse>> response = ResponseEntity.ok(
                CourierConverter.convertCouriersToSimpleCourierResponses(couriers));
        log.debug(RESPONSE, response);
        return response;
    }

    @GetMapping("/withDetails")
    public ResponseEntity<List<CreateCourierResponse>> getAllCouriersWithDetails() {
        log.debug("Getting all couriers");
        List<Courier> couriers = courierService.getAllCouriers();
        if (couriers.isEmpty()) {
            log.info("No couriers found");
            return ResponseEntity.noContent().build();
        }

        log.info("Found {} couriers", couriers.size());
        ResponseEntity<List<CreateCourierResponse>> response = ResponseEntity.ok(
                CourierConverter.convertCouriersToCreateCourierResponses(couriers));
        log.debug(RESPONSE, response);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourierById(@PathVariable Long id) {
        log.debug("Get courier by ID {}", id);
        Courier courier = courierService.getCourierById(id);
        if (courier == null) {
            log.info(NO_COURIER_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("courier", "ID", id.toString()));
        }

        log.info("Found courier with ID {}", id);
        ResponseEntity<CreateCourierResponse> response = ResponseEntity.ok(
                CourierConverter.convertCourierToCreateCourierResponse(courier));
        log.debug(RESPONSE, response);
        return response;
    }

    @PostMapping
    public ResponseEntity<?> addCourier(@Valid @RequestBody CreateCourierRequest createCourierRequest,
                                        BindingResult bindingResult) {
        log.debug("Add courier {}", createCourierRequest);
        if (bindingResult.hasErrors()) {
            log.error("Validation errors: {}", bindingResult.getFieldErrors());
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Courier courier = courierService.addCourier(createCourierRequest);
        ResponseEntity<CreateCourierResponse> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(CourierConverter.convertCourierToCreateCourierResponse(courier));
        log.debug(RESPONSE, response);
        return response;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCourier(@Valid @RequestBody UpdateCourierRequest updateCourierRequest,
                                           @PathVariable Long id, BindingResult bindingResult) {
        log.debug("Update courier {}", updateCourierRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Courier updatedCourier = courierService.updateCourier(id, updateCourierRequest);
        if (updatedCourier == null) {
            log.info(NO_COURIER_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("courier", "ID", id.toString()));
        }

        log.debug("Updated courier with ID {} with data {}", id, updatedCourier);
        ResponseEntity<CreateCourierResponse> response = ResponseEntity.status(HttpStatus.OK)
                .body(CourierConverter.convertCourierToCreateCourierResponse(updatedCourier));
        log.debug(RESPONSE, response);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourierById(@PathVariable Long id) {
        log.debug("Delete courier by ID {}", id);
        Courier courier = courierService.getCourierById(id);
        if (courier == null) {
            log.info(NO_COURIER_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("courier", "ID", id.toString()));
        }

        courierService.deleteCourierById(id);
        log.info("Deleted courier with ID {}", id);
        return ResponseEntity.noContent().build();
    }
}
