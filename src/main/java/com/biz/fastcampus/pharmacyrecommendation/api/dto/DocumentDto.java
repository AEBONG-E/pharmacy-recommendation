package com.biz.fastcampus.pharmacyrecommendation.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentDto {

    @JsonProperty("address_name")
    private String addressName; // 전체 지번 주소 또는 전체 도로명 주소, 입력에 따라 결정됨
//    private String addressType; // address_name 값의 타입 (REGION: 지명, ROAD: 도로명, REGION_ADDR: 지번 주소, ROAD_ADDR: 도로명 주소
    @JsonProperty("y")
    private double latitude;           // y 좌표값, 위도
    @JsonProperty("x")
    private double longitude;          // x 좌표값, 경도

}
