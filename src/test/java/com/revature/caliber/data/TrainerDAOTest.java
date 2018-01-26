package com.revature.caliber.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Trainer;
import com.revature.caliber.beans.TrainerRole;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrainerDAOTest {

	private static final Logger log = Logger.getLogger(TrainerDAOTest.class);

	private int trainerId = 1;
	private String email = "patrick.walsh@revature.com";

	@Autowired
	TrainerDAO dao;

	Trainer test;

	@Before
	public void initialize() {
		log.info("Initalizing a Test Trainer for use in Tests");
		test = new Trainer();
		test.setName("TRAINER_NAME");
		test.setTitle("TRAINER_TITLE");
		test.setEmail("TRAINER@EMAIL.COM");
		test.setTier(TrainerRole.ROLE_INACTIVE);
	}

	@Test
	public void testFindAll() {
		log.info("Getting All Trainers");
		List<Trainer> test = dao.findAll();
		assertFalse(test.isEmpty());
	}
	
	@Test
	public void findByTrainerId() {
		log.info("Getting by trainerId");
		Trainer trainer = dao.findByTrainerId(trainerId);
		assertEquals(trainerId, trainer.getTrainerId());
	}
	
	@Test
	public void addTrainer() {
		log.info("Adding Trainer");
		Trainer savedTrainer = dao.save(test);
		assertEquals(test.getTrainerId(), savedTrainer.getTrainerId());
	}
	
	@Test
	public void updateTrainer() {
		log.info("Updating Trainer");
		Trainer savedTrainer = dao.save(test);
		savedTrainer.setTitle("UPDATED_TITLE");
		Trainer updatedTrainer = dao.save(savedTrainer);
		assertEquals(savedTrainer.getTitle(), updatedTrainer.getTitle());
	}
	
	@Test
	public void deleteTrainer() {
		log.info("Deleting Trainer");
		Trainer savedTrainer = dao.save(test);
		dao.delete(savedTrainer);
		assertNull(dao.findByTrainerId(savedTrainer.getTrainerId()));
	}
	
	@Test
	public void findAllTrainerTitles() {
		log.info("Getting All Trainer Titles");
		List<String> titles = dao.findAllTrainerTitles();
		assertFalse(titles.isEmpty());
	}
	
	@Test
	public void findByEmail() {
		log.info("Getting Trainer by Email");
		Trainer trainer = dao.findByEmail(email);
		assertEquals(email, trainer.getEmail());
	}
}
