package org.example.deliverysystemapi.converters;

import org.example.deliverysystemapi.dto.CreateCustomerResponse;
import org.example.deliverysystemapi.dto.SimpleCustomerResponse;
import org.example.deliverysystemapi.entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerConverter {

    private CustomerConverter() {
    }

    public static CreateCustomerResponse convertCustomerToCreateCustomerResponse(Customer customer) {
        CreateCustomerResponse createCustomerResponse = null;
        if (customer != null) {
            createCustomerResponse = new CreateCustomerResponse();
            createCustomerResponse.setId(customer.getId());
            createCustomerResponse.setName(customer.getName());
            createCustomerResponse.setSurname(customer.getSurname());
            createCustomerResponse.setPhoneNo(customer.getPhoneNo());
            createCustomerResponse.setAddress(customer.getAddress());
            createCustomerResponse.setEmail(customer.getEmail());
        }
        return createCustomerResponse;
    }

    public static List<CreateCustomerResponse> convertCustomersToCreateCustomerResponses(List<Customer> customers) {
        List<CreateCustomerResponse> createCustomerResponses = null;
        if (customers != null) {
            createCustomerResponses = new ArrayList<>();
            for (Customer customer : customers) {
                createCustomerResponses.add(convertCustomerToCreateCustomerResponse(customer));
            }
        }
        return createCustomerResponses;
    }

    public static SimpleCustomerResponse convertCustomerToSimpleCustomerResponse(Customer customer) {
        SimpleCustomerResponse simpleCustomerResponse = null;
        if (customer != null) {
            simpleCustomerResponse = new SimpleCustomerResponse();
            simpleCustomerResponse.setId(customer.getId());
            simpleCustomerResponse.setName(customer.getName());
            simpleCustomerResponse.setSurname(customer.getSurname());
        }
        return simpleCustomerResponse;
    }

    public static List<SimpleCustomerResponse> convertCustomersToSimpleCustomerResponses(List<Customer> customers) {
        List<SimpleCustomerResponse> simpleCustomerResponses = null;
        if (customers != null) {
            simpleCustomerResponses = new ArrayList<>();
            for (Customer customer : customers) {
                simpleCustomerResponses.add(convertCustomerToSimpleCustomerResponse(customer));
            }
        }
        return simpleCustomerResponses;
    }
}
