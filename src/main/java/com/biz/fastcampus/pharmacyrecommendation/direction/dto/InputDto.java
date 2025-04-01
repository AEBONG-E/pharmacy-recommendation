package com.biz.fastcampus.pharmacyrecommendation.direction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InputDto {
    private String address;
}
