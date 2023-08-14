package com.shazzad.auditdemo.dto;

import java.util.List;

public record EmployeeRequest(
    Long id,
    String name,
    String department,
    String companyName,
    List<Long> addressIds
) {
}
