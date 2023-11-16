package com.medilabo.doctors;


import com.medilabo.doctors.configuration.MongoDBDataInitializer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoctorsApplication {

	@Autowired
	private MongoDBDataInitializer mongoDBDataInitializer;

	public static void main(String[] args) {
		SpringApplication.run(DoctorsApplication.class, args);
	}

	@PostConstruct
	public void initializeDatabase() {
		mongoDBDataInitializer.initializeMongoDB();
	}

}
