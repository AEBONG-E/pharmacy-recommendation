package com.biz.fastcampus.pharmacyrecommendation.api.pharmacy.repository;

import com.biz.fastcampus.pharmacyrecommendation.api.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

}
