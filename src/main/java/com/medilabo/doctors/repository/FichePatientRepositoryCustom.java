package com.medilabo.doctors.repository;

import java.util.List;

public interface FichePatientRepositoryCustom {

    long countKeywordOccurrencesForPatient(Integer patientId, List<String> keywords);

}
