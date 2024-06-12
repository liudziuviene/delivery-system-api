package org.example.deliverysystemapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliverysystemapi.dto.CreateCustomerRequest;
import org.example.deliverysystemapi.dto.UpdateCustomerRequest;
import org.example.deliverysystemapi.entities.Customer;
import org.example.deliverysystemapi.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

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
        return customerRepository.saveAndFlush(customer);
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
        return customerRepository.saveAndFlush(customer);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
