package org.example.deliverysystemapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCourierRequest {
    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 2, max = 30, message = "{validation.constraints.size.message}")
    private final String name;
    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 2, max = 30, message = "{validation.constraints.size.message}")
    private final String surname;
    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 3, max = 20, message = "{validation.constraints.size.message}")
    private final String vehicleType;
    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 7, max = 15, message = "{validation.constraints.size.message}")
    private final String phoneNo;
    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 10, max = 50, message = "{validation.constraints.size.message}")
    private final String address;
    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 10, max = 40, message = "{validation.constraints.size.message}")
    private final String email;
}
