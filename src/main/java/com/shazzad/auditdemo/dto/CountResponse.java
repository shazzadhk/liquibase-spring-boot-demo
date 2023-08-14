package com.shazzad.auditdemo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CountResponse {

    private String departmentName;
    private long employeeCount;
}
