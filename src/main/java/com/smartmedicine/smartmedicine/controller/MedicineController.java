package com.smartmedicine.smartmedicine.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.smartmedicine.smartmedicine.model.Medicine;
import com.smartmedicine.smartmedicine.model.user;
import com.smartmedicine.smartmedicine.repository.MedicineRepository;
import com.smartmedicine.smartmedicine.repository.UserRepository;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;

    public MedicineController(
            MedicineRepository medicineRepository,
            UserRepository userRepository) {

        this.medicineRepository = medicineRepository;
        this.userRepository = userRepository;
    }

    // ADD MEDICINE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medicine addMedicine(
            @RequestParam Long userId,
            @RequestBody MedicineRequest request) {

        user patient = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Patient not found"
                        ));

        Medicine medicine = new Medicine();

        medicine.setMedicineName(request.medicineName());
        medicine.setDosage(request.dosage());
        medicine.setFrequency(request.frequency());

        medicine.setPrescribedTime(
                LocalTime.parse(request.prescribedTime())
        );

        medicine.setStartDate(
                LocalDate.parse(request.startDate())
        );

        medicine.setEndDate(
                LocalDate.parse(request.endDate())
        );

        medicine.setStatus("PENDING");
        medicine.setUser(patient);

        return medicineRepository.save(medicine);
    }

    // GET MEDICINES FOR PATIENT
    @GetMapping
    public List<Medicine> getMedicines(
            @RequestParam Long userId) {

        return medicineRepository.findByUserId(userId);
    }

    // MARK MEDICINE AS TAKEN
    @PutMapping("/{id}/taken")
    public Medicine markTaken(
            @PathVariable Long id) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Medicine not found"
                        ));

        medicine.setStatus("TAKEN");

        return medicineRepository.save(medicine);
    }

    // MARK MEDICINE AS MISSED
    @PutMapping("/{id}/missed")
    public Medicine markMissed(
            @PathVariable Long id) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Medicine not found"
                        ));

        medicine.setStatus("MISSED");

        return medicineRepository.save(medicine);
    }

    // DELETE MEDICINE
    @DeleteMapping("/{id}")
    public String deleteMedicine(
            @PathVariable Long id) {

        if (!medicineRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Medicine not found"
            );
        }

        medicineRepository.deleteById(id);

        return "Medicine deleted successfully";
    }

    public record MedicineRequest(
            String medicineName,
            String dosage,
            String frequency,
            String prescribedTime,
            String startDate,
            String endDate
    ) {
    }
}