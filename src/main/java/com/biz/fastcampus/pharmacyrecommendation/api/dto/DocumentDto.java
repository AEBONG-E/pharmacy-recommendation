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

    @JsonProperty("place_name")
    private String placeName;          // 장소명(약국명)

    @JsonProperty("address_name")
    private String addressName;        // 전체 지번 주소 또는 전체 도로명 주소, 입력에 따라 결정됨

    @JsonProperty("y")
    private double latitude;           // y 좌표값, 위도

    @JsonProperty("x")
    private double longitude;          // x 좌표값, 경도

    @JsonProperty("distance")
    private double distance;           // 거리 계산값

}
