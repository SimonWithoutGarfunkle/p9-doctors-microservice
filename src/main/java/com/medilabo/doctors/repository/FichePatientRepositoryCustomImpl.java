package com.medilabo.doctors.repository;

import com.medilabo.doctors.model.FichePatient;
import com.medilabo.doctors.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Custom implementation of the FichePatientRepositoryCustom interface
 * providing additional methods for handling patient notes.
 */
@Repository
public class FichePatientRepositoryCustomImpl implements FichePatientRepositoryCustom {

    private MongoTemplate mongoTemplate;

    private static Logger logger = LoggerFactory.getLogger(FichePatientRepositoryCustomImpl.class);

    @Autowired
    public void FichePatientRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Add the count of occurence for each word of the list
     * @param patientId patient to count in his notes
     * @param searchStrings list of words to search
     * @return long number of occurences of all words in the list
     */
    @Override
    public long countKeywordOccurrencesForPatient(Integer patientId, List<String> searchStrings) {
        long totalOccurrences = 0;

        for (String word : searchStrings) {
            totalOccurrences += countOccurrencesInNotes(patientId, word);
        }

        return totalOccurrences;
    }

    /**
     * Retrieve the notes of the patient from DB and count word occurence in the notes
     * @param patientId patient to count in his notes
     * @param wordToSearch word to search
     * @return number of occurence
     */
    public long countOccurrencesInNotes(Integer patientId, String wordToSearch) {
        Query query = new Query(Criteria.where("patientId").is(patientId));
        FichePatient patient = mongoTemplate.findOne(query, FichePatient.class);

        if (patient == null) {
            return 0L;
        }

        long totalOccurrences = 0;
        Pattern pattern = Pattern.compile("(?i)\\b" + wordToSearch + "\\b");

        for (Note noteText : patient.getNotes()) {
            Matcher matcher = pattern.matcher(noteText.getTexte());
            while (matcher.find()) {
                totalOccurrences++;
            }
        }

        return totalOccurrences;
    }


}
