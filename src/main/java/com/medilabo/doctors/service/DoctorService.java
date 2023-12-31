package com.medilabo.doctors.service;

import com.medilabo.doctors.model.FichePatient;
import com.medilabo.doctors.model.Note;
import com.medilabo.doctors.repository.FichePatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final FichePatientRepository fichePatientRepository;

    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);

    @Autowired
    public DoctorService(FichePatientRepository fichePatientRepository) { this.fichePatientRepository=fichePatientRepository; }

    public FichePatient getFichePatientByPatientId(Integer patientId) {
        logger.info("get FichePatient of patientId : "+patientId);
        return fichePatientRepository.findByPatientId(patientId);
    }

    public FichePatient addFichePatient(FichePatient fichePatient) {
        logger.debug("Saving fiche n°"+fichePatient.getPatientId()+" in database");
        return fichePatientRepository.insert(fichePatient);
    }

    public FichePatient addNote(Integer patientId, Note note) {
        logger.debug("Adding note to patient n°"+patientId);
        FichePatient fiche = getFichePatientByPatientId(patientId);
        fiche.getNotes().add(note);
        return fichePatientRepository.save(fiche);

    }

    public long analyseNotes(Integer id, List<String> mots) {
        logger.info("analyzing notes from patient n°"+id);
        return fichePatientRepository.countKeywordOccurrencesForPatient(id, mots);
    }

}
