package com.revature.caliber;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CaliberSpringDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaliberSpringDataApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner runner() {
		return args -> {
//			Panel panelSave = panelDao.save();
//			System.out.println(panelSave);
			
//			List<Panel> panelUpdate = panelDao.update((Panel) panelByPanelId);
//			System.out.println(panelUpdate);

//			for(Trainee t : panelTraineesAndPanelsPerBatch) {
//				for(Panel p : t.getPanelInterviews())
//					System.out.println(p);
//			}
		};
	}
}
