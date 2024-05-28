package org.example.deliverysystemapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCourierRequest {
    private String name;
    private String surname;
    private String vehicleType;
    private String phoneNo;
    private String address;
    private String email;
}
