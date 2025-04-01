package com.biz.fastcampus.pharmacyrecommendation.api.pharmacy.service;

import com.biz.fastcampus.pharmacyrecommendation.api.dto.DocumentDto;
import com.biz.fastcampus.pharmacyrecommendation.api.dto.KakaoApiResponseDto;
import com.biz.fastcampus.pharmacyrecommendation.api.service.KakaoAddressSearchService;
import com.biz.fastcampus.pharmacyrecommendation.direction.dto.OutputDto;
import com.biz.fastcampus.pharmacyrecommendation.direction.entity.Direction;
import com.biz.fastcampus.pharmacyrecommendation.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public List<OutputDto> recommendPharmacyList(String address) {

        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address: {}",  address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        // 공공기관 약국 데이터 및 거리계산 알고리즘
//        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        // Kakao 카테고리 장소 검색 API 적용
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }

    // todo: directionUrl, roadViewUrl 값 추가 필요
    private OutputDto convertToOutputDto(Direction direction) {
        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl("todo")
                .roadViewUrl("todo")
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }

}
