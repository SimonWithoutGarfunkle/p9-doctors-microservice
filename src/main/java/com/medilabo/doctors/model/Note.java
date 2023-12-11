package com.medilabo.doctors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    private String id;

    private String docteur;
    private String texte;
    private String date;

    public void setId(String id) {
        this.id = id;
    }

}
