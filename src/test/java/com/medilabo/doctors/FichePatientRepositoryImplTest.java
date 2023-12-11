package com.medilabo.doctors;

import com.medilabo.doctors.model.FichePatient;
import com.medilabo.doctors.model.Note;
import com.medilabo.doctors.repository.FichePatientRepositoryCustomImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FichePatientRepositoryImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private FichePatientRepositoryCustomImpl fichePatientRepositoryCustom;

    private FichePatient patientTest = new FichePatient();

    private List<String> searchStrings = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        patientTest.setPatientId(1);
        List<Note> notesTest = new ArrayList<>();
        notesTest.add(createNote("2023-03-19T00:00:00.000Z", "David Tennant", "Ceci est une note pour les tests avec 0 trigger word"));
        patientTest.setNotes(notesTest);
        searchStrings.add("testmot");

    }

    private static Note createNote(String date, String docteur, String texte) {
        Note note = new Note();
        note.setDocteur(docteur);
        note.setTexte(texte);
        note.setDate(date);
        return note;
    }

    @Test
    public void count0KeywordOccurrencesForPatientTest() {
        //Arrange
        when(mongoTemplate.findOne(any(Query.class), eq(FichePatient.class))).thenReturn(patientTest);

        //Act
        long result = fichePatientRepositoryCustom.countKeywordOccurrencesForPatient(1, searchStrings);

        //Assert
        assertEquals(0, result);
    }

    @Test
    public void count1KeywordOccurrencesForPatientTest() {
        //Arrange
        Note nouvelleNote = new Note("1", "David Tennant", "cette note contient testmot ", "2023-03-19T00:00:00.000Z");
        patientTest.getNotes().add(nouvelleNote);
        when(mongoTemplate.findOne(any(Query.class), eq(FichePatient.class))).thenReturn(patientTest);

        //Act
        long result = fichePatientRepositoryCustom.countKeywordOccurrencesForPatient(1, searchStrings);

        //Assert
        assertEquals(1, result);

    }

    @Test
    public void countNullKeywordOccurrencesForPatientTest() {
        //Arrange
        when(mongoTemplate.findOne(any(Query.class), eq(FichePatient.class))).thenReturn(null);

        //Act
        long result = fichePatientRepositoryCustom.countKeywordOccurrencesForPatient(1, searchStrings);

        //Assert
        assertEquals(0, result);

    }


}
