package com.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^7\\d{10}$", message = "Phone must start with 7 and contain 11 digits")
    @Size(min = 11, max = 11, message = "Phone must be exactly 11 digits")
    private String phone;
} 