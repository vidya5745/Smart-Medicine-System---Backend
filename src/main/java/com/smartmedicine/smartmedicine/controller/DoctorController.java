package com.smartmedicine.smartmedicine.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.smartmedicine.smartmedicine.model.user;
import com.smartmedicine.smartmedicine.repository.DoctorPatientRepository;
import com.smartmedicine.smartmedicine.repository.UserRepository;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final UserRepository userRepository;
    private final DoctorPatientRepository doctorPatientRepository;

    public DoctorController(
            UserRepository userRepository,
            DoctorPatientRepository doctorPatientRepository) {

        this.userRepository = userRepository;
        this.doctorPatientRepository = doctorPatientRepository;
    }

    @GetMapping("/{doctorId}/patients")
    public List<user> getPatients(
            @PathVariable Long doctorId) {

        user doctor = userRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Doctor not found"
                        ));

        // Check whether the logged-in user is a doctor
        if (!"DOCTOR".equalsIgnoreCase(doctor.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "User is not a doctor"
            );
        }

        // Get patients assigned to this doctor
        return doctorPatientRepository
                .findPatientsByDoctorId(doctorId);
    }
}