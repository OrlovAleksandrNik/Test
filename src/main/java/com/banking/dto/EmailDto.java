package com.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 200, message = "Email must not exceed 200 characters")
    private String email;
} 