package com.smartmedicine.smartmedicine.repository;

import com.smartmedicine.smartmedicine.model.DoctorPatient;
import com.smartmedicine.smartmedicine.model.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorPatientRepository
        extends JpaRepository<DoctorPatient, Long> {

    @Query("""
        SELECT dp.patient
        FROM DoctorPatient dp
        WHERE dp.doctor.id = :doctorId
    """)
    List<user> findPatientsByDoctorId(@Param("doctorId") Long doctorId);
}