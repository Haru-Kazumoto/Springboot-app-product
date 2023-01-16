package dev.pack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SupplierDto {

    @NotEmpty(message = "NAME IS REQUIRED")
    private String name;

    @NotEmpty(message = "EMAIL IS REQUIRED")
    @Email(message = "EMAIL PATTERN NOT VALID")
    private String email;

    @NotEmpty(message = "ADDRESS IS REQUIRED")
    private String address;
}
