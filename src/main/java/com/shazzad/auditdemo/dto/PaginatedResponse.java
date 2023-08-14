package com.shazzad.auditdemo.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedResponse <T>{

    private int size;
    private long totalElements;
    private int totalPages;
    private int currentPages;
    private List<T> contents;
}
