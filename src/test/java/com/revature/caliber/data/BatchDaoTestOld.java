package com.revature.caliber.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Instant;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import com.revature.caliber.beans.TrainerRole;
import com.revature.caliber.beans.TrainingStatus;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BatchDaoTestOld {
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	@Autowired
	private BatchDAO dao;
	
	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao#findAllCurrent(trainerId)
	 */
	@Test
	public void findAllCurrentIntTest() {
		List<Batch> batches = dao.findAllCurrent(1);
		int expected = 3; //only 3 current batches with trainerId: 1
		int actual = batches.size();
		assertEquals(expected, actual);
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao#findAllCurrentWithNotes()
	 */
	@Test
	public void findAllCurrentWithNotesTest() {
		List<Batch> batches = dao.findAllCurrentWithNotes();
		int expected = 1; // only one current batch has notes
		int actual = batches.size();
		assertEquals(expected, actual);
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao#findAllCurrentWithNotesAndTrainees()
	 */
	@Test
	public void findAllCurrentWithNotesAndTraineesTest() {
		List<Batch> batches = dao.findAllCurrentWithNotesAndTrainees();
		int expected = 1; // Only one current batch has notes and trainees
		int actual = batches.size();
		assertEquals(expected, actual);
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao#findAllCurrentWithTrainees()
	 */
	@Test
	public void findAllCurrentWithTraineesTest() {
		List<Batch> batches = dao.findAllCurrentWithTrainees();
		int expected = 7; // All current batches have trainees
		int actual = batches.size();
		assertEquals(expected, actual);
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao#findAll()
	 */
	@Test
	public void findAllTest() {
		String sql = "SELECT * FROM CALIBER_BATCH";
		int expect = jdbcTemplate.queryForList(sql).size();
		int actual = dao.findAll().size();
		List<Batch> test =dao.findAll();
		for(int i=0;i<test.size();i++) {
			test.get(i).getTrainees().forEach(x->System.out.println(x));
		}
		assertEquals(expect, actual);
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao#findAllAfterDate()
	 */
	@Test
	public void findAllAfterDateTest() {
		// positive test
		// find how many after a specific date
		int expect = 9;
		int actual = dao.findAllAfterDate(1, 1, 2017).size();
		dao.findAllAfterDate(1, 1, 2017).forEach(x->System.out.println(x.getStartDate()));
		assertEquals(expect, actual);
		// negative test
		String sql = "SELECT START_DATE FROM CALIBER_BATCH WHERE START_DATE >= '2017-01-01'";
		expect = jdbcTemplate.queryForList(sql).size();
		actual = dao.findAllAfterDate(Integer.MAX_VALUE, 1, 2017).size();
		// If SQL statement found at least 1 batch start date they should not
		// equal. Otherwise they both should be equal
		if (expect > 0) {
			assertNotEquals(expect, actual);
		} else {
			assertEquals(expect, actual);
		}
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao#findAllByTrainer() Returns
	 *      batches with specified trainer and co-trainer without dropped
	 *      trainees
	 */
	@Test
	public void findAllByTrainerTest() {
		// positive testing
		String sql = "SELECT TRAINER_ID FROM CALIBER_TRAINER WHERE ROWNUM = 1";
		Integer trainerId = jdbcTemplate.queryForObject(sql, Integer.class);
		sql = "SELECT * FROM CALIBER_BATCH WHERE TRAINER_ID = " + trainerId + " OR CO_TRAINER_ID = " + trainerId;
		int expect = jdbcTemplate.queryForList(sql).size();
		List<Batch> batches = dao.findAllByTrainer(trainerId);
		int actual = batches.size();
		assertEquals(expect, actual);

		// Make sure dropped trainees are not included
		// Testing against batch 2150 with trainer Patrick Walsh
		
		expect = 13;
		for (int i = 0; i < batches.size(); i++) {
			if (batches.get(i).getBatchId() == 2150) {
				batches.get(i).getTrainees().forEach(x->System.out.println(x.getTrainingStatus()));
				actual = batches.get(i).getTrainees().size();
				break;
			}
		}
		assertEquals(expect, actual);

		// negative testings
		trainerId = Integer.MIN_VALUE;
		expect = 0;
		actual = dao.findAllByTrainer(trainerId).size();
		assertEquals(expect, actual);
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao#findAllCurrent() The dao
	 *      findAllCurrent takes into account 30 days before the current date.
	 *      It also removes dropped trainees from the batches returned
	 */
	@Test
	public void findAllCurrentTest() {
		// This allows for 1 month flexibility. This was needed because in
		// dao, the query takes into account 1 month ago as 'current'
		int endDateLimit = 30;
		String sql = "SELECT * FROM CALIBER_BATCH WHERE END_DATE+" + endDateLimit
				+ " >= TO_DATE(SYSDATE,'YYYY/MM/DD') AND START_DATE <= TO_DATE(SYSDATE,'YYYY/MM/DD');";
		int expect = jdbcTemplate.queryForList(sql).size();
		List<Batch> batches = dao.findAllCurrent();
		batches.forEach(x->System.out.println(x.getBatchId()));
		int actual = batches.size();
		assertEquals(expect, actual);

		// Test to make sure it does not count dropped trainees
		// Testing batch id 2201 because it has 4 dropped from 20
		expect = 16;
		for (int i = 0; i < batches.size(); i++) {
			if (batches.get(i).getBatchId() == 2201) {
				actual = batches.get(i).getTrainees().size();
				break;
			}
		}
		assertEquals(expect, actual);
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao.findOne(Integer batchId)
	 * Find a known batch, assert that the IDs are the same.
	 * Try to find a batch that doesn't exist, fail if it does.
	 */
	@Test
	public void findOneTest() {
		int expected = 2050;
		int actual = dao.findOne(expected).getBatchId();
		assertEquals(expected, actual);
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao.findOneWithDroppedTrainees(Integer
	 *      batchId) Finds from a batch with known dropped trainees, upon
	 *      finding a single trainee with the TrainingStatus of Dropped, calls
	 *      it good.
	 */
	@Test
	public void findOneWithDroppedTraineesTest() {
		boolean success = false;
		Set<Trainee> resultSet = dao.findOneWithDroppedTrainees(2150).getTrainees();
		for (Trainee resultSetTrainee : resultSet) {
			if (resultSetTrainee.getTrainingStatus() == TrainingStatus.Dropped) {
				success = true;
				break;
			}
		}
		assertTrue(success);
		try{
			dao.findOneWithDroppedTrainees(-999).getTrainees();
			fail();
		}
		catch(Exception e){
		}
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao.findOneWithTraineesAndGrades(Integer batchId)
	 * Finds from an existing batch, fails if any trainees have a dropped status.
	 * Tries to find from a non-existing batch, fails if no exception gets thrown.
	 */
	@Test
	public void findOneWithTraineesAndGradesTest() {
		String expected = "1602 Feb08 Java";
		String actual = dao.findOneWithTraineesAndGrades(2050).getTrainingName();
		assertEquals(expected, actual);
		boolean success = true;
		Set<Trainee> resultSet = dao.findOneWithTraineesAndGrades(2050).getTrainees();
		for (Trainee resultSetTrainee : resultSet) {
			if (resultSetTrainee.getTrainingStatus() == TrainingStatus.Dropped) {
				success = false;
				break;
			}
		}
		assertTrue(success);
		try{
			dao.findOneWithTraineesAndGrades(-999).getTrainees();
			fail();
		}
		catch(Exception e){
		}
	}

	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.dao.update()
	 * This test needs the findOne method to work.
	 * It finds a batch from the database, changes a value, updates the database,
	 * loads the batch from the database again.
	 * Tries to update a non-existing batch.
	 */
	@Test
	public void updateTest() {
		Batch testBatch = dao.findOne(2050);
		testBatch.setLocation("The basement");
		dao.save(testBatch);
		Batch updatedTestBatch = dao.findOne(2050);
		assertEquals(updatedTestBatch.getLocation(),"The basement");
		try{
			testBatch.setBatchId(-984);
			dao.save(testBatch);
			fail();
		}
		catch(Exception e){
		}
	}

	/**
	 * Tests methods:
	 * 
	 * @see com.revature.caliber.data.dao.save(Batch batch)
	 */
	@Test
	public void saveTest() {
		Trainer testTrainer = new Trainer("Sir. Test", "Tester", "test@test.test", TrainerRole.ROLE_TRAINER);
//		testTrainer.setTrainerId(2);
//		Trainer testTrainer = entityManager.find(Trainer.class, 1);
		Batch testBatch = new Batch("Test Name", testTrainer, Date.from(Instant.now()),
				Date.from(Instant.now().plus(Period.ofDays(60))), "Test Location");
		dao.saveAndFlush(testBatch);
		List<Batch> resultSet = dao.findAll();
		boolean success = false;
		for (Batch found : resultSet) {
			if ("Test Location".equals(found.getLocation())) {
				success = true;
				break;
			}
		}
		assertTrue(success);
	}
}
