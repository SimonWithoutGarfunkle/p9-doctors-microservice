package com.medilabo.doctors.configuration;

import com.medilabo.doctors.controller.DoctorController;
import com.medilabo.doctors.model.FichePatient;
import com.medilabo.doctors.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class MongoDBDataInitializer {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static Logger logger = LoggerFactory.getLogger(MongoDBDataInitializer.class);



    public void initializeMongoDB() {
        mongoTemplate.remove(new Query(), "notes");

        List<FichePatient> fichesPatients = new ArrayList<>();

        // Fiche Patient 1
        FichePatient fichePatient1 = new FichePatient();
        fichePatient1.setPatientId(1);
        List<Note> notes1 = new ArrayList<>();
        notes1.add(createNote("2023-03-19T00:00:00.000Z", "David Tennant", "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"));
        fichePatient1.setNotes(notes1);
        fichesPatients.add(fichePatient1);

        // Fiche Patient 2
        FichePatient fichePatient2 = new FichePatient();
        fichePatient2.setPatientId(2);
        List<Note> notes2 = new ArrayList<>();
        notes2.add(createNote("2022-12-22T00:00:00.000Z", "Matt Smith", "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"));
        notes2.add(createNote("2022-06-20T00:00:00.000Z", "Matt Smith", "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"));
        fichePatient2.setNotes(notes2);
        fichesPatients.add(fichePatient2);

        // Fiche Patient 3
        FichePatient fichePatient3 = new FichePatient();
        fichePatient3.setPatientId(3);
        List<Note> notes3 = new ArrayList<>();
        notes3.add(createNote("2022-12-05T00:00:00.000Z", "Jodie Whittaker", "Le patient déclare qu'il fume depuis peu"));
        notes3.add(createNote("2022-09-14T00:00:00.000Z", "Ncuti Gatwa", "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"));
        fichePatient3.setNotes(notes3);
        fichesPatients.add(fichePatient3);

        // Fiche Patient 4
        FichePatient fichePatient4 = new FichePatient();
        fichePatient4.setPatientId(4);
        List<Note> notes4 = new ArrayList<>();
        notes4.add(createNote("2023-01-27T00:00:00.000Z", "Peter Capaldi", "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"));
        notes4.add(createNote("2023-08-07T00:00:00.000Z", "David Tennant", "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"));
        notes4.add(createNote("2023-04-26T00:00:00.000Z", "Peter Capaldi", "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"));
        notes4.add(createNote("2022-04-25T00:00:00.000Z", "Jodie Whittaker", "Taille, Poids, Cholestérol, Vertige et Réaction"));
        fichePatient4.setNotes(notes4);
        fichesPatients.add(fichePatient4);

        mongoTemplate.insert(fichesPatients, "notes");

        logger.info("Données insérées dans MongoDB !");
    }

    private static Note createNote(String date, String docteur, String texte) {
        Note note = new Note();
        note.setDocteur(docteur);
        note.setTexte(texte);
        note.setDate(date);
        return note;
    }
}
