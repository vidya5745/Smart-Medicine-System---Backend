package com.smartmedicine.smartmedicine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor_patient")
public class DoctorPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private user doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private user patient;

    public DoctorPatient() {
    }

    public Long getId() {
        return id;
    }

    public user getDoctor() {
        return doctor;
    }

    public user getPatient() {
        return patient;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctor(user doctor) {
        this.doctor = doctor;
    }

    public void setPatient(user patient) {
        this.patient = patient;
    }
}