package com.medilabo.doctors.controller;

import com.medilabo.doctors.model.FichePatient;
import com.medilabo.doctors.model.Note;
import com.medilabo.doctors.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/{id}")
    public FichePatient getFichePatientById(@PathVariable(value = "id") Integer id) {
        return doctorService.getFichePatientByPatientId(id);
    }

    @PostMapping("/createFiche/{id}")
    public FichePatient createFichePatient(@PathVariable(value = "id") Integer id, FichePatient fiche) {
        fiche.setPatientId(id);
        return doctorService.addFichePatient(fiche);
    }

    @PostMapping("/{id}")
    public FichePatient addNoteToFichePatient(@PathVariable(value = "id") Integer id, Note note) {
        return doctorService.addNote(id, note);
    }


}
