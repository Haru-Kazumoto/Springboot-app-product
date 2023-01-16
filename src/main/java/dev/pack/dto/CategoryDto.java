package dev.pack.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryDto {

    @NotEmpty(message = "NAME IS REQUIRED")
    @Column(name = "Column-name")
    private String name;
}
