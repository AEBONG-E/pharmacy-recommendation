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
        String inputAddress = "대구광역시 남구 현충로16길 1"
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
        String inputAddress = "대구광역시 남구 현충로16길 1"
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

}
