package com.smartmedicine.smartmedicine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmedicine.smartmedicine.model.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByUserId(Long userId);

}