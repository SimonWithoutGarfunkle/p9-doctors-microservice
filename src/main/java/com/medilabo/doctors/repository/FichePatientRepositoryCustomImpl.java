package com.medilabo.doctors.repository;

import com.medilabo.doctors.model.KeywordOccurrencesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FichePatientRepositoryCustomImpl implements FichePatientRepositoryCustom {

    private MongoTemplate mongoTemplate;

    @Autowired
    public void FichePatientRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public long countKeywordOccurrencesForPatient(Integer patientId, List<String> keywords) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("patientId").is(patientId)),
                Aggregation.unwind("notes"),
                Aggregation.match(Criteria.where("notes.texte").regex(String.join("|", keywords))),
                Aggregation.group().count().as("totalOccurrences")
        );

        AggregationResults<KeywordOccurrencesResult> results = mongoTemplate.aggregate(aggregation, "notes", KeywordOccurrencesResult.class);
        if (!results.getMappedResults().isEmpty()) {
            return results.getMappedResults().get(0).getTotalOccurrences();
        } else {
            return 0L;
        }
    }


}
