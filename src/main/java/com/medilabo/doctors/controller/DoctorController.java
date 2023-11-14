package com.medilabo.doctors.controller;

import com.medilabo.doctors.model.FichePatient;
import com.medilabo.doctors.model.Note;
import com.medilabo.doctors.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    private static Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @GetMapping("/{id}")
    public FichePatient getFichePatientById(@PathVariable(value = "id") Integer id) {
        return doctorService.getFichePatientByPatientId(id);
    }

    @PostMapping("/createFiche/{id}")
    public FichePatient createFichePatient(@PathVariable(value = "id") Integer id,@RequestBody FichePatient fiche) {
        logger.info("creating fiche for patient n°"+id);
        fiche.setPatientId(id);
        return doctorService.addFichePatient(fiche);
    }

    @PostMapping("/{id}")
    public FichePatient addNoteToFichePatient(@PathVariable(value = "id") Integer id,@RequestBody Note note) {
        logger.info("adding note from docteur "+note.getDocteur()+" to patient n°"+id);
        return doctorService.addNote(id, note);
    }


}
