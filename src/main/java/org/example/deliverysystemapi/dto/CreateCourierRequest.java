package org.example.deliverysystemapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCourierRequest {
    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;
    @NotBlank(message = "Surname must not be blank")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String surname;
    @NotBlank(message = "Vehicle type must not be blank")
    @Size(min = 3, max = 20, message = "Vehicle type must be between 3 and 20 characters")
    private String vehicleType;
    @NotBlank(message = "Phone number must not be blank")
    @Size(min = 7, max = 15, message = "Phone number must be between 7 and 15 characters")
    private String phoneNo;
    @NotBlank(message = "Address must not be blank")
    @Size(min = 5, max = 50, message = "Address must be between 5 and 50 characters")
    private String address;
    @NotBlank(message = "Email must not be blank")
    @Size(min = 7, max = 40, message = "Email must be between 7 and 40 characters")
    private String email;
}
