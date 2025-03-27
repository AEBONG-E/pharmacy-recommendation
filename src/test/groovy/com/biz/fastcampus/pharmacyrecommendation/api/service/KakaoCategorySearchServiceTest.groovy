package com.biz.fastcampus.pharmacyrecommendation.api.service

import com.biz.fastcampus.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoCategorySearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired KakaoCategorySearchService kakaoCategorySearchService;

    def "정확한 위도, 경도, 반경 값을 입력하면, 카카오 카테고리 검색 API 결과를 반환한다."() {
        given:
        double latitude = 128.580791758483
        double longitude = 35.8393037253215
        Integer radius = 10

        when:
        def searchResult = kakaoCategorySearchService.requestPharmacyCategorySearch(latitude, longitude, radius)

        then:
        searchResult.getDocumentList().size() > 0

    }

}
