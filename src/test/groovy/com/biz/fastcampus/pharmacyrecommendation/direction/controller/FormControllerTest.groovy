package com.biz.fastcampus.pharmacyrecommendation.direction.controller

import com.biz.fastcampus.pharmacyrecommendation.api.pharmacy.service.PharmacyRecommendationService
import com.biz.fastcampus.pharmacyrecommendation.direction.dto.OutputDto
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

class FormControllerTest extends Specification {

    private MockMvc mockMvc
    private PharmacyRecommendationService pharmacyRecommendationService = Mock()
    private List<OutputDto> outputDtoList

    def setup() {
        mockMvc = standaloneSetup(new FormController(pharmacyRecommendationService)).build()

        outputDtoList = new ArrayList<>()
        outputDtoList.addAll(
                OutputDto.builder()
                        .pharmacyName("pharmacy1")
                        .build(),
                OutputDto.builder()
                        .pharmacyName("pharmacy2")
                        .build()
        )
    }

    def "GET /"() {
        expect:
        // FormController 의 "/" URI를 GET 방식으로 호출
        mockMvc.perform(get("/"))
        .andExpect(handler().handlerType(FormController.class))
        .andExpect(handler().methodName("main"))
        .andExpect(status().isOk())
        .andExpect(view().name("main"))
        .andDo(print())
    }

    def "POST /search"() {
        given:
        String inputAddress = "서울 성북구 종암동"

        when:
        def resultActions = mockMvc.perform(post("/search").param("address", inputAddress))

        then:
        1 * pharmacyRecommendationService.recommendPharmacyList(argument -> {
            assert argument == inputAddress // mock 객체의 argument 검증
        }) >> outputDtoList

        resultActions
            .andExpect(status().isOk())
            .andExpect(view().name("output"))
            .andExpect(model().attributeExists("outputFormList")) // model 에 outputFormList 라는 key가 존재하는지 확인
            .andExpect(model().attribute("outputFormList", outputDtoList))
            .andDo(print())
    }

}
