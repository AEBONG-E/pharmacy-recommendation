package com.biz.fastcampus.pharmacyrecommendation.api.pharmacy.service

import com.biz.fastcampus.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import com.biz.fastcampus.pharmacyrecommendation.api.pharmacy.entity.Pharmacy
import com.biz.fastcampus.pharmacyrecommendation.api.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepositoryService pharmacyRepositoryService;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    def setup() {
        this.pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository update - dirty checking success"() {
        given:
        String inputAddress = "서울특별시 성북구 종암동"
        String modifiedAddress = "대구광역시 수성구 동대구로 86"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build()

        when:
        def entity = this.pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(entity.getId(), modifiedAddress)
        def result = this.pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == modifiedAddress

    }

    def "PharmacyRepository update - dirty checking fail"() {
        given:
        String inputAddress = "서울특별시 성북구 종암동"
        String modifiedAddress = "대구광역시 수성구 동대구로 86"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build()

        when:
        def entity = this.pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddressWithoutTransactional(entity.getId(), modifiedAddress)
        def result = this.pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == inputAddress

    }

    def "self invocation test -> 트랜잭션이 적용되지 않는다. (롤백 적용 X)"() {
        given:
        String address = "서울특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        pharmacyRepositoryService.bar(Arrays.asList(pharmacy))

        then:
        def e = thrown(RuntimeException.class)
        def result = pharmacyRepositoryService.findAll()
        result.size() == 1
    }

    def "transactional read only test"() {
        given:
        String inputAddress = "서울특별시 성북구"
        String modifiedAddress = "서울특별시 광진구"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def input = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def pharmacy = pharmacyRepository.save(input)
        pharmacyRepositoryService.startReadOnlyMethod(pharmacy.id)

        then:
        def result = pharmacyRepositoryService.findAll()
        result.get(0).getPharmacyAddress() == inputAddress

    }

}
