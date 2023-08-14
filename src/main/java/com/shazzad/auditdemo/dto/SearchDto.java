package com.shazzad.auditdemo.dto;

public record SearchDto(
    String columnName,
    String value
) {
}
