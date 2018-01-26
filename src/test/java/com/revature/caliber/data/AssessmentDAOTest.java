package com.revature.caliber.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

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
import com.revature.caliber.beans.Trainer;
import com.revature.caliber.beans.TrainerRole;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AssessmentDAOTest {

	@Autowired
	AssessmentDAO dao;

	Assessment test;

	static final long ASSESSMENT_ID = 2077;
	static final short WEEK = 5;
	static final short BATCH_ID = 2150;

	@Before
	public void initialize() {
		test = new Assessment();
		test.setTitle("TEST_ASSESSMENT");
		Trainer trainer = new Trainer("NAME", "TITLE", "EMAIL@EMAIL.COM", TrainerRole.ROLE_INACTIVE);
		Batch batch = new Batch("TRAINING_NAME", trainer, new Date(), new Date(), "LOCATION");
		test.setBatch(batch);
		test.setRawScore(77);
		test.setType(AssessmentType.Other);
		test.setWeek(WEEK);
		test.setCategory(new Category());
		test.setGrades(new HashSet<Grade>());
		
	}

	@Test
	public void testFindAll() {
		List<Assessment> test = dao.findAll();
		assertFalse(test.isEmpty());
	}

	@Test
	public void testFindByAssessmentId() {
		Assessment test = dao.findByAssessmentId(ASSESSMENT_ID);
		assertFalse(test == null);
	}

	@Test
	public void testAddAssessment() {
		Assessment savedAssessment = dao.save(test);
		assertEquals(test.getAssessmentId(), savedAssessment.getAssessmentId());
	}

	@Test
	public void testUpdateAssessment() {
		Assessment savedTest = dao.save(test);
		Assessment retrievedAssessment = dao.findByAssessmentId(savedTest.getAssessmentId());
		retrievedAssessment.setTitle("UPDATED_ASSESSMENT");
		Assessment updatedAssessment = dao.save(retrievedAssessment);
		assertEquals(savedTest, updatedAssessment);
	}

	@Test
	public void testDeleteAssessment() {
		Assessment savedTest = dao.save(test);
		dao.delete(savedTest);
		assertNull(dao.findByAssessmentId(savedTest.getAssessmentId()));
	}

	@Test
	public void findByWeekNumber() {
		List<Assessment> assessments = dao.findByWeek(WEEK);
		for (Assessment a : assessments) {
			if (a.getWeek() != WEEK)
				Assert.fail();
		}
		assertFalse(assessments.isEmpty());
	}

	@Test
	public void findByBatchId() {
		List<Assessment> assessments = dao.findByBatchBatchId(BATCH_ID);
		for (Assessment a : assessments) {
			if (a.getBatch().getBatchId() != BATCH_ID)
				Assert.fail();
		}
		assertFalse(assessments.isEmpty());
	}

	@Test
	public void findByBatchIdAndWeek() {
		List<Assessment> assessments = dao.findByBatchBatchIdAndWeek(BATCH_ID, WEEK);
		for (Assessment a : assessments) {
			if (a.getBatch().getBatchId() != BATCH_ID || a.getWeek() != WEEK)
				Assert.fail();
		}
		assertFalse(assessments.isEmpty());
	}
}
