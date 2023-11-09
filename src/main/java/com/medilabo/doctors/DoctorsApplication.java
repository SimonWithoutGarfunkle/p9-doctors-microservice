package com.medilabo.doctors;

import com.medilabo.doctors.model.LightNote;
import com.medilabo.doctors.model.Note;
import com.medilabo.doctors.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DoctorsApplication implements CommandLineRunner {

	@Autowired
	private NoteRepository noteRepository;

	private final Logger logger = LoggerFactory.getLogger(DoctorsApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(DoctorsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Note note = new Note();
		note.setDocteur("denis");
		note.setTexte("ceci est un exemple de texte");
		note.setPatientId(2);
		note.setDate(new Date(System.currentTimeMillis()));
		noteRepository.save(note);

		Note note2 = new Note();
		note2.setDocteur("2denis");
		note2.setTexte("22222ceci est un exemple de texte");
		note2.setPatientId(222);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1); // Soustrait un jour
		note2.setDate(new Date(cal.getTimeInMillis()));
		noteRepository.save(note2);

		Note note3 = new Note();
		note3.setDocteur("3333denis");
		note3.setTexte("33333ceci est un exemple de texte");
		note3.setPatientId(333);
		cal.add(Calendar.DAY_OF_MONTH, -5); // Soustrait un jour
		note3.setDate(new Date(cal.getTimeInMillis()));
		noteRepository.save(note3);

		Note note4 = new Note();
		note4.setDocteur("4444denis");
		note4.setTexte("4444ceci est un exemple de texte");
		note4.setPatientId(444);
		cal.add(Calendar.DAY_OF_MONTH, +10); // Soustrait un jour
		note4.setDate(new Date(cal.getTimeInMillis()));
		noteRepository.save(note4);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");


		List<Note> allNotes = noteRepository.findAll();
		allNotes.stream().forEach((uneNote) -> logger.info(uneNote.getTexte()));

		List<LightNote> list = noteRepository.findByOrderByDateDesc();
		list.forEach((lightNote)-> logger.info("Le docteur "+lightNote.getDocteur()+" a enregistré ses notes le "+sdf.format(lightNote.getDate())));



	}


}
