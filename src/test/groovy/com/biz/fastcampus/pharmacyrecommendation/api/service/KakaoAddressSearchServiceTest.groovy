package com.biz.fastcampus.pharmacyrecommendation.api.service

import com.biz.fastcampus.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "address 파라미터 값이 Null 이면, requestAddressSearch 메소드는 Null 을 리턴한다."() {
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "주소값이 valid 하다면, requestAddressSearch 메소드는 정상적으로 document 를 반환한다."() {
        given:
        def address = "서울특별시 성북구 종암동"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        println("address: " + address)
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }

}
