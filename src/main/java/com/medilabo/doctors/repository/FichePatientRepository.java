package com.medilabo.doctors.repository;

import com.medilabo.doctors.model.FichePatient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichePatientRepository extends MongoRepository<FichePatient, String> {

    FichePatient findByPatientId(Integer id);

}
