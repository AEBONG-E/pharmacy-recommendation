package com.biz.fastcampus.pharmacyrecommendation.direction.controller;

import com.biz.fastcampus.pharmacyrecommendation.api.pharmacy.service.PharmacyRecommendationService;
import com.biz.fastcampus.pharmacyrecommendation.direction.dto.InputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FormController {

    private final PharmacyRecommendationService pharmacyRecommendationService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputFormList",
                               this.pharmacyRecommendationService.recommendPharmacyList(inputDto.getAddress()));
        return modelAndView;
    }

}
