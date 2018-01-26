package com.revature.caliber.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Assessment;
import com.revature.caliber.beans.AssessmentType;
import com.revature.caliber.beans.Batch;
import com.revature.caliber.beans.Category;
import com.revature.caliber.beans.Grade;
import com.revature.caliber.beans.Trainee;
import com.revature.caliber.beans.Trainer;
import com.revature.caliber.beans.TrainerRole;
import com.revature.caliber.beans.TrainingStatus;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AssessmentDAOTest {

	private static final Logger log = Logger.getLogger(AssessmentDAOTest.class);

	private long assessmentId = 2077;
	private short week = 5;
	private short batchId = 2150;

	@Autowired
	AssessmentDAO dao;

	Assessment test;

	@Before
	public void initialize() {
		log.info("Initalizing a Test Assessment for use in Tests");
		test = new Assessment();
		test.setTitle("TEST_ASSESSMENT");
		Trainer trainer = new Trainer("NAME", "TITLE", "EMAIL@EMAIL.COM", TrainerRole.ROLE_INACTIVE);
		Batch batch = new Batch("TRAINING_NAME", trainer, new Date(), new Date(), "LOCATION");
		test.setBatch(batch);
		test.setRawScore(77);
		test.setType(AssessmentType.Other);
		test.setWeek(week);
		test.setCategory(new Category());
		test.setGrades(new HashSet<Grade>());
	}

	@Test
	public void testFindAll() {
		log.info("Getting All Assessments");
		List<Assessment> test = dao.findAll();
		assertFalse(test.isEmpty());
	}

	@Test
	public void testFindByAssessmentId() {
		log.info("Getting Assessment by assessmentId");
		Assessment test = dao.findByAssessmentId(assessmentId);
		assertFalse(test == null);
	}

	@Test
	public void testAddAssessment() {
		log.info("Adding Assessment");
		Assessment savedAssessment = dao.save(test);
		assertEquals(test.getAssessmentId(), savedAssessment.getAssessmentId());
	}

	@Test
	public void testUpdateAssessment() {
		log.info("Updating Assessment");
		Assessment savedAssessment = dao.save(test);
		savedAssessment.setTitle("UPDATED_ASSESSMENT");
		Assessment updatedAssessment = dao.save(savedAssessment);
		assertEquals(savedAssessment, updatedAssessment);
	}

	@Test
	public void testDeleteAssessment() {
		log.info("Deleting Assessment");
		Assessment savedTest = dao.save(test);
		dao.delete(savedTest);
		assertNull(dao.findByAssessmentId(savedTest.getAssessmentId()));
	}

	@Test
	public void findByWeekNumber() {
		log.info("Getting Assessment by Week Number");
		List<Assessment> assessments = dao.findDistinctByWeek(week);
		for (Assessment a : assessments) {
			if (a.getWeek() != week)
				Assert.fail("week Number does not match: " + a.toString());
		}
		assertFalse(assessments.isEmpty());
	}

	@Test
	public void findByBatchId() {
		log.info("Getting Assessment by batchId");
		List<Assessment> assessments = dao.findDistinctByBatchBatchId(batchId);
		for (Assessment a : assessments) {
			if (a.getBatch().getBatchId() != batchId)
				Assert.fail("batchId does not match: " + a.toString());
			for (Trainee t: a.getBatch().getTrainees()) {
				if (t.getTrainingStatus() == TrainingStatus.Dropped) {
					Assert.fail("Found Dropped Student: " + t.toString());
				}
			}
		}
		assertFalse(assessments.isEmpty());
	}

	@Test
	public void findByBatchIdAndWeek() {
		log.info("Getting Assessment by batchId and Week");
		List<Assessment> assessments = dao.findByBatchIdAndWeek(batchId, week);
		for (Assessment a : assessments) {
			if (a.getBatch().getBatchId() != batchId)
				Assert.fail("batchId does not match: " + a.toString());
			else if (a.getWeek() != week)
				Assert.fail("week Number does not match: " + a.toString());
			for (Trainee t: a.getBatch().getTrainees()) {
				if (t.getTrainingStatus() == TrainingStatus.Dropped) {
					Assert.fail("Found Dropped Student: " + t.toString());
				}
			}
		}
		assertFalse(assessments.isEmpty());
	}
}
