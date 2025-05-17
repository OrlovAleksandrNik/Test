package com.example.test.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private BigDecimal balance;
    private List<String> emails;
    private List<String> phones;
}
