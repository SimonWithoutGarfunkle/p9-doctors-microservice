package com.medilabo.doctors.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class Note {

    @Id
    private String id;

    private String docteur;
    private String texte;
    private String date;



    public void setId(String id) {
        this.id = id;
    }


    public void setDocteur(String docteur) {
        this.docteur = docteur;
    }


    public void setTexte(String texte) {
        this.texte = texte;
    }


    public void setDate(String date) {
        this.date = date;
    }
}
