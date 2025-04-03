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
import org.springframework.web.util.UriComponentsBuilder;

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

    private final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    private final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    public List<OutputDto> recommendPharmacyList(String address) {

        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address: {}",  address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        // 공공기관 약국 데이터 및 거리계산 알고리즘
        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        // Kakao 카테고리 장소 검색 API 적용
        // List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }

    // todo: directionUrl, roadViewUrl 값 추가 필요
    private OutputDto convertToOutputDto(Direction direction) {

        String params = String.join(",", direction.getTargetPharmacyName(),
                                  String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));

        String directionUrl = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params).toUriString();

        log.info("direction params: {}, url: {}", params, directionUrl);

        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl(directionUrl)
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }

}
