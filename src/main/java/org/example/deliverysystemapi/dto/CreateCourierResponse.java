package org.example.deliverysystemapi.dto;

import lombok.Data;

@Data
public class CreateCourierResponse {
    private Long id;
    private String name;
    private String surname;
    private String vehicleType;
    private String phoneNo;
    private String address;
    private String email;
}
