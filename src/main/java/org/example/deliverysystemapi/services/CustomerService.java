package org.example.deliverysystemapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliverysystemapi.dto.CreateCustomerRequest;
import org.example.deliverysystemapi.dto.UpdateCustomerRequest;
import org.example.deliverysystemapi.entities.Customer;
import org.example.deliverysystemapi.exceptions.DuplicateCustomerException;
import org.example.deliverysystemapi.repositories.CustomerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {
    public final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            log.warn("Customer with ID {} not found", id);
        }
        return customer;
    }

    public Customer addCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer();
        if (createCustomerRequest != null) {
            customer.setName(createCustomerRequest.getName());
            customer.setSurname(createCustomerRequest.getSurname());
            customer.setPhoneNo(createCustomerRequest.getPhoneNo());
            customer.setAddress(createCustomerRequest.getAddress());
            customer.setEmail(createCustomerRequest.getEmail());
        }
        try {
            return customerRepository.saveAndFlush(customer);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error adding customer: {}", ex.getMessage());
            throw new DuplicateCustomerException("Phone number or email already exists");
        }
    }

    public Customer updateCustomer(Long id, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            log.warn("Customer with ID {} not found", id);
            return null;
        }

        if (updateCustomerRequest.getName() != null) {
            customer.setName(updateCustomerRequest.getName());
        }
        if (updateCustomerRequest.getSurname() != null) {
            customer.setSurname(updateCustomerRequest.getSurname());
        }
        if (updateCustomerRequest.getPhoneNo() != null) {
            customer.setPhoneNo(updateCustomerRequest.getPhoneNo());
        }
        if (updateCustomerRequest.getAddress() != null) {
            customer.setAddress(updateCustomerRequest.getAddress());
        }
        if (updateCustomerRequest.getEmail() != null) {
            customer.setEmail(updateCustomerRequest.getEmail());
        }
        try {
            return customerRepository.saveAndFlush(customer);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error updating customer: {}", ex.getMessage());
            throw new DuplicateCustomerException("Phone number or email already exists");
        }
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> filterCustomers(String name, String surname) {
        if (name != null && !name.isEmpty()) {
            return customerRepository.findByName(name);
        } else if (surname != null && !surname.isEmpty()) {
            return customerRepository.findBySurname(surname);
        } else {
            return customerRepository.findAll();
        }
    }

    public Customer filterCustomersByUniqueFields(String phoneNo, String email) {
        if (phoneNo != null && !phoneNo.isEmpty()) {
            return customerRepository.findByPhoneNo(phoneNo);
        } else if (email != null && !email.isEmpty()) {
            return customerRepository.findByEmail(email);
        } else {
            return null;
        }
    }
}
