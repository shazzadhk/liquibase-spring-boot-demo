package com.shazzad.auditdemo.dto;

public record EmployeeSearchRequest(
    String employeeSearchText,
    String orderFieldName,
    int currentPage,
    int elementSize,
    String orderDir
) {
}
