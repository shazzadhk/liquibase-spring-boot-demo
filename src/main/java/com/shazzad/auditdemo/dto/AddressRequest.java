package com.shazzad.auditdemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRequest(
    @NotNull(message = "Employee ID can't be null")
    Long id,
    @NotBlank(message = "City name can't be blank")
    String city,
    @NotBlank(message = "road Number can't be blank")
    String roadNumber,
    @NotNull(message = "employee ID can't be null")
    Long employeeId
) { }
