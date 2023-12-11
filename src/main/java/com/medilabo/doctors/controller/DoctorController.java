package com.medilabo.doctors.controller;

import com.medilabo.doctors.model.FichePatient;
import com.medilabo.doctors.model.Note;
import com.medilabo.doctors.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Rest Controller to access the back end Doctor
 */
@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    private static Logger logger = LoggerFactory.getLogger(DoctorController.class);

    /**
     * get the Fiche patient of the patient from the mongo DB
     *
     * @param id of the patient
     * @return fiche of the patient
     */
    @GetMapping("/{id}")
    public FichePatient getFichePatientById(@PathVariable(value = "id") Integer id) {
        logger.debug("Retrieving fiche from patient n°"+id);
        return doctorService.getFichePatientByPatientId(id);
    }

    /**
     * Create the fiche for the patient and save it in the mongo db
     *
     * @param id of the patient
     * @param fiche to create
     * @return the new fiche
     */
    @PostMapping("/createFiche/{id}")
    public FichePatient createFichePatient(@PathVariable(value = "id") Integer id,@RequestBody FichePatient fiche) {
        logger.info("creating fiche for patient n°"+id);
        fiche.setPatientId(id);
        return doctorService.addFichePatient(fiche);
    }

    /**
     * Add the note to the fiche of the patient
     *
     * @param id of the patient
     * @param note to save
     * @return the updated fiche with the new note
     */
    @PostMapping("/{id}")
    public FichePatient addNoteToFichePatient(@PathVariable(value = "id") Integer id,@RequestBody Note note) {
        logger.info("adding note from docteur "+note.getDocteur()+" to patient n°"+id);
        return doctorService.addNote(id, note);
    }

    /**
     * Performs analysis based on the provided list of words for a specific patient9.
     *
     * @param id of the patient to analyse
     * @param mots The list of words to search in the history of the patient
     * @return Occurrence of the specified words in the history of the patient
     */
    @GetMapping("/{id}/analyse")
    public long analyseNotes(@PathVariable(value = "id") Integer id,@RequestParam("mots") List<String> mots) {
        logger.info("analysing patient n°" + id + " looking for " + mots.size() + " trigger words");
        List<String> decodedMots = null;
        if (mots != null) {
            decodedMots = mots.stream()
                    .map(mot -> URLDecoder.decode(mot, StandardCharsets.UTF_8))
                    .toList();
            logger.debug("list of trigger words adapted to UTF_8");
        }
        return doctorService.analyseNotes(id, decodedMots);
    }

}
