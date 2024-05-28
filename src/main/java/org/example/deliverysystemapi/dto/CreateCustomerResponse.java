package org.example.deliverysystemapi.dto;

import lombok.Data;

@Data
public class CreateCustomerResponse {
    private Long id;
    private String name;
    private String surname;
    private String phoneNo;
    private String address;
    private String email;
}
