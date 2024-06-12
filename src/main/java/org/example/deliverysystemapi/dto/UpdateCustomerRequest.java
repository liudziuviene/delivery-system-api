package org.example.deliverysystemapi.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerRequest {
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;
    @Size(min = 2, max = 30, message = "Surname must be between 2 and 30 characters")
    private String surname;
    @Size(min = 7, max = 15, message = "Phone number must be between 7 and 15 characters")
    private String phoneNo;
    @Size(min = 5, max = 50, message = "Address must be between 5 and 50 characters")
    private String address;
    @Size(min = 7, max = 40, message = "Email must be between 7 and 40 characters")
    private String email;
}
