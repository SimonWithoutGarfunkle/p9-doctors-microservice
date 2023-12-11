package com.medilabo.doctors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.doctors.configuration.MongoDBDataInitializer;
import com.medilabo.doctors.model.FichePatient;
import com.medilabo.doctors.model.Note;
import com.medilabo.doctors.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoDBDataInitializer mongoDBDataInitializer;


    @BeforeEach
    public void setUp() {
        mongoDBDataInitializer.initializeMongoDB();
    }

    @Test
    public void getFichePatientByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctor/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientId", is(1)));
    }

    @Test
    public void createFichePatientTest() throws Exception {
        assertNull(doctorService.getFichePatientByPatientId(5));
        FichePatient fiche = new FichePatient();
        fiche.setPatientId(5);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/doctor/createFiche/"+fiche.getPatientId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(fiche)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertNotNull(doctorService.getFichePatientByPatientId(5));
    }

    @Test
    public void addNoteToFichePatientTest() throws Exception {
        Note note = new Note();
        note.setTexte("note de test");
        note.setDocteur("test docteur");
        int notesSize = doctorService.getFichePatientByPatientId(1).getNotes().size();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/doctor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(note)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        FichePatient fiche = objectMapper.readValue(responseContent, FichePatient.class);

        assertEquals(notesSize + 1 , fiche.getNotes().size());

    }

    @Test
    public void analyseNotesTest() throws Exception {
        Note note = new Note();
        note.setTexte("azertyuiopqsdfghjklmwxcvbn");
        doctorService.addNote(1, note);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/doctor/1/analyse")
                .param("mots", "azertyuiopqsdfghjklmwxcvbn"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Long responseContent = Long.parseLong(result.getResponse().getContentAsString());
        assertEquals(1L , responseContent);

    }



}
