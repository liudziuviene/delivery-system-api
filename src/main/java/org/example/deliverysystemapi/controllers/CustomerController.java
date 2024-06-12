package org.example.deliverysystemapi.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliverysystemapi.converters.CustomerConverter;
import org.example.deliverysystemapi.dto.CreateCustomerRequest;
import org.example.deliverysystemapi.dto.CreateCustomerResponse;
import org.example.deliverysystemapi.dto.SimpleCustomerResponse;
import org.example.deliverysystemapi.dto.UpdateCustomerRequest;
import org.example.deliverysystemapi.entities.Customer;
import org.example.deliverysystemapi.exceptions.NotFoundErrorResponse;
import org.example.deliverysystemapi.exceptions.ValidationErrorResponse;
import org.example.deliverysystemapi.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private static final String NO_CUSTOMER_FOUND = "No customer found with ID {}";
    private static final String RESPONSE = "Response {}";

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<SimpleCustomerResponse>> getAllCustomers() {
        log.debug("Get names and surnames of all customers");
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            log.info("No customers found");
            return ResponseEntity.noContent().build();
        }
        log.info("Found {} customers", customers.size());
        ResponseEntity<List<SimpleCustomerResponse>> response = ResponseEntity.ok(
                CustomerConverter.convertCustomersToSimpleCustomerResponses(customers));
        log.debug(RESPONSE, response);
        return response;
    }

    @GetMapping("/withDetails")
    public ResponseEntity<List<CreateCustomerResponse>> getAllCustomersWithDetails() {
        log.debug("Get all customers");
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            log.info("No customers found");
            return ResponseEntity.noContent().build();
        }

        log.info("Found {} customers", customers.size());
        ResponseEntity<List<CreateCustomerResponse>> response = ResponseEntity.ok(
                CustomerConverter.convertCustomersToCreateCustomerResponses(customers));
        log.debug(RESPONSE, response);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        log.debug("Get customer by ID: {}", id);
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            log.info(NO_CUSTOMER_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("customer", "ID", id.toString()));
        }
        log.info("Found customer with ID: {}", id);
        ResponseEntity<CreateCustomerResponse> response = ResponseEntity.ok()
                .body(CustomerConverter.convertCustomerToCreateCustomerResponse(customer));
        log.debug(RESPONSE, response);
        return response;
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest,
                                         BindingResult bindingResult) {
        log.debug("Adding customer {}", createCustomerRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Customer customer = customerService.addCustomer(createCustomerRequest);
        ResponseEntity<CreateCustomerResponse> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(CustomerConverter.convertCustomerToCreateCustomerResponse(customer));
        log.debug(RESPONSE, response);
        return response;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody UpdateCustomerRequest updateCustomerRequest,
                                            @PathVariable Long id, BindingResult bindingResult) {

        log.debug("Update customer {}", updateCustomerRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Customer updatedCustomer = customerService.updateCustomer(id, updateCustomerRequest);
        if (updatedCustomer == null) {
            log.info(NO_CUSTOMER_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("customer", "ID", id.toString()));
        }

        log.debug("Updated customer with ID {} with data {}", id, updatedCustomer);
        ResponseEntity<CreateCustomerResponse> response = ResponseEntity.status(HttpStatus.OK)
                .body(CustomerConverter.convertCustomerToCreateCustomerResponse(updatedCustomer));
        log.debug(RESPONSE, response);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        log.debug("Delete customer by ID: {}", id);
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            log.info(NO_CUSTOMER_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("customer", "ID", id.toString()));
        }
        customerService.deleteCustomerById(id);
        log.info("Deleted customer with ID {}", id);
        return ResponseEntity.noContent().build();
    }
}
