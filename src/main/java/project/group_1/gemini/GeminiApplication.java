package project.group_1.gemini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.gemini.app.ocs.OCS;
@SpringBootApplication
public class GeminiApplication {

	public static void main(String[] args) {
		OCS ocs = new OCS(true);
		System.out.println("ocs");
		System.out.println(ocs.getAllSciencePlans());
		SpringApplication.run(GeminiApplication.class, args);
	}

}
