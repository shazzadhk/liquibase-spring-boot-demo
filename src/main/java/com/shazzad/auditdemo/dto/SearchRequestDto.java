package com.shazzad.auditdemo.dto;

import java.util.List;

public record SearchRequestDto(
    List<SearchDto> searchDtoList,
    String searchType
) {
}
