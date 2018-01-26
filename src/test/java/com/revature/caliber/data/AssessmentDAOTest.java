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

	@Before
	public void initialize() {
		test = new Assessment();
		test.setTitle("TEST_ASSESSMENT");
		Trainer trainer = new Trainer("NAME", "TITLE", "EMAIL@EMAIL.COM", TrainerRole.ROLE_INACTIVE);
		Batch batch = new Batch("TRAINING_NAME", trainer, new Date(), new Date(), "LOCATION");
		test.setBatch(batch);
		test.setRawScore(77);
		test.setType(AssessmentType.Other);
		test.setWeek((short) 3);
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
		Assessment test = dao.findByAssessmentId(2077);
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
		List<Assessment> assessments = dao.findByWeek((short) 5);
		for (Assessment a : assessments) {
			if (a.getWeek() != (short) 5) Assert.fail();
		}
		assertFalse(assessments.isEmpty());
	}

	// @Test
	// public void findByBatchId() {
	//
	// }

}
