package com.medilabo.doctors.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "notes")
public class FichePatient {
    @Id
    private String id;

    private String docteurPrincipal;

    private Integer patientId;

    List<Note> notes;

}
