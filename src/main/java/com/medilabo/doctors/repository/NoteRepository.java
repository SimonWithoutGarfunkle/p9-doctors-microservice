package com.medilabo.doctors.repository;

import com.medilabo.doctors.model.LightNote;
import com.medilabo.doctors.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    public List<LightNote> findByOrderByDateDesc();

}
