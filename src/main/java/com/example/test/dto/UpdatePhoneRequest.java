package com.example.test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePhoneRequest {

    @NotBlank
    @Pattern(regexp = "7\\d{10}", message = "Phone must be in format 79207865432")
    private String phone;
}
