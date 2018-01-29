package com.revature.caliber.data;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Batch;
import com.revature.caliber.beans.Trainee;
import com.revature.caliber.beans.Trainer;
@RunWith(SpringRunner.class)
@DataJpaTest
public class TraineeDAOTest {
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	private static final String TRAINEE_COUNT = "select count(trainee_id) from caliber_trainee";
	@Autowired
	TraineeDAO dao;
	@Test
	public void test() {
		List<Trainee> listOfTrainees = dao.findAllNotDropped();
		assertFalse(listOfTrainees.isEmpty());
		
		
		System.out.println(dao.findByTraineeId(1));
	}
	@Test
	public void testSave() {
		Batch batch = new Batch("String trainingName", entityManager.find(Trainer.class, 1), Date.from(Instant.now()), Date.from(Instant.now()), "String location");
//		batch.setBatchId(2200);
//		int batch_id=(int) entityManager.persistAndGetId(batch);
//		entityManager.flush();
		String name = "Danny McQuack";
		String email = "test@anotherDomain.com";
//		Batch pBatch = entityManager.find(Batch.class, batch_id);
		Trainee trainee = new Trainee(name, null, email, batch);
		Long before = jdbcTemplate.queryForObject(TRAINEE_COUNT, Long.class);
		dao.saveAndFlush(trainee);

		try {
			Trainee nullName = new Trainee(null, null, email, batch);
			dao.save(nullName);

		} catch (ConstraintViolationException e) {
			e.getLocalizedMessage();
		}
		try {
			Trainee nullEmail = new Trainee(name, null, null, batch);
			dao.save(nullEmail);
		} catch (ConstraintViolationException e) {
			e.getLocalizedMessage();
		}
		try {
			Trainee nullBatch = new Trainee(name, null, email, null);
			dao.save(nullBatch);
		} catch (ConstraintViolationException e) {
			e.getLocalizedMessage();
		}
		Long after = jdbcTemplate.queryForObject(TRAINEE_COUNT, Long.class);
		assertEquals(++before, after);
	}

	@Test
	public void testFindAll() {
	
		Long sizeActual = jdbcTemplate.queryForObject(TRAINEE_COUNT, Long.class);
		List<Trainee> trainees = dao.findAll();
		Long sizeExpected = (long) trainees.size();
		assertEquals(sizeExpected, sizeActual);
	}
	
	@Test
	public void testFindAllNotDropped() {
	
		Long sizeActual = jdbcTemplate.queryForObject(TRAINEE_COUNT + " WHERE training_status != 'Dropped'", Long.class);
		List<Trainee> trainees = dao.findAllNotDropped();
		Long sizeExpected = (long) trainees.size();
		assertEquals(sizeExpected, sizeActual);
	}
	
	@Test
	public void testFindByTraineeId() {
		Trainee fbtId = dao.findByTraineeId(1);
		assertEquals(fbtId.getName(), "Howard Johnson");
		
	}
	
	@Test
	public void testFindAllbyBatchId() {
//		List<Trainee> listOfTrainees = dao.findAllByBatch(2100);
//		System.out.println(listOfTrainees);
		Batch batch = new Batch();
		batch.setBatchId(2200);
		String traineeCountByBatch = TRAINEE_COUNT + " where batch_id = " + batch.getBatchId()
				+ " and training_status != 'Dropped'";
		List<Trainee> trainees = dao.findAllByBatch(batch.getBatchId());
		System.out.println("Hello");
		trainees.forEach(x->x.getGrades().forEach(y->System.out.println(y.getScore())));
		Long actualBatchSize = jdbcTemplate.queryForObject(traineeCountByBatch, Long.class);
		Long expectedBatchSize = (long) trainees.size();
		assertEquals(expectedBatchSize, actualBatchSize);
		int badBatchId = 5; // no batch with this Id exists
		List<Trainee> badBatchCall = dao.findAllByBatch(badBatchId);
		int badBatchCallSize = badBatchCall.size();
		assertEquals(0, badBatchCallSize);

		
	
	}
	
	@Test
	public void testFindAllDroppedByBatch() {
		

		Batch batch = new Batch();
		batch.setBatchId(2200);
		String traineeCountByBatch = TRAINEE_COUNT + " where batch_id = " + batch.getBatchId()
				+ " and training_status = 'Dropped'";
		Long actualBatchDroppedSize = jdbcTemplate.queryForObject(traineeCountByBatch, Long.class);
		List<Trainee> droppedTrainees = dao.findAllDroppedByBatch(batch.getBatchId());
		Long expectedBatchDroppedSize = (long) droppedTrainees.size();
		assertEquals(expectedBatchDroppedSize, actualBatchDroppedSize);

		int badBatchId = 5; // no batch with this Id exists
		List<Trainee> badBatchCall = dao.findAllDroppedByBatch(badBatchId);
		int badBatchCallSize = badBatchCall.size();
		assertEquals(0, badBatchCallSize);
	}
	
	@Test
	public void testFindAllByTrainer() {
		
		Trainer trainer = new Trainer();
			trainer.setTrainerId(1);
		String traineeCountByTrainer = TRAINEE_COUNT + " WHERE BATCH_ID IN "
				+ "(SELECT BATCH_ID FROM CALIBER_BATCH WHERE TRAINER_ID = " + trainer.getTrainerId() + ") "
				+ " AND TRAINING_STATUS != 'Dropped'";
		Long actualCountSize = jdbcTemplate.queryForObject(traineeCountByTrainer, Long.class);
		List<Trainee> trainees = dao.findAllByTrainer(trainer.getTrainerId());
		Long expectedCountSize = (long) trainees.size();
		assertEquals(expectedCountSize, actualCountSize);

		int badTrainerId = -1; // no trainer with this Id exists
		List<Trainee> badTrainerCall = dao.findAllByTrainer(badTrainerId);
		int badTrainerCallSize = badTrainerCall.size();
		assertEquals(0, badTrainerCallSize);
	}
	
	@Test
	public void testFindOne() {
	
		String actual = "osher.y.cohen@gmail.com";
		assertEquals(actual, dao.findOneTrainee(5503).getEmail());
	}
	
	@Test
	public void testFindByEmail() {
		
		Integer id = 5503;
		assertEquals((int) id, (int) dao.findByEmail("osher").get(0).getTraineeId());
	}
	
	@Test
	public void testFindByName() {
		
		Integer id = 5511;
		assertEquals((int) id, (int) dao.findByName("Lau").get(0).getTraineeId());
	}
	
	@Test
	public void testFindBySkypeId() {
		
		Integer id = 5504;
		assertEquals((int) id, (int) dao.findBySkypeId("kyle.chang").get(0).getTraineeId());
	}
	
	@Test
	public void testUpdate() {
		
		String updatedName = "Up, Dated";
		Trainee trainee = dao.findOneTrainee(5503);
		trainee.setName(updatedName);
		dao.save(trainee);
		assertEquals(updatedName, trainee.getName());
	}
	
	@Test
	public void testDelete() {
		
		int initialSize = dao.findAll().size();
		Trainee toDelete = dao.findOne(5503);
		dao.delete(toDelete);
		int newSize = dao.findAll().size();
		assertEquals(--initialSize, newSize);
	}

}
