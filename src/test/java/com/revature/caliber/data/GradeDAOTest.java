package com.revature.caliber.data;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

import java.util.Date;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Assessment;
import com.revature.caliber.beans.Batch;
import com.revature.caliber.beans.Category;
import com.revature.caliber.beans.Grade;
import com.revature.caliber.beans.Trainee;
import com.revature.caliber.beans.Trainer;
@RunWith(SpringRunner.class)
@DataJpaTest

public class GradeDAOTest {
	
	
	private static final Logger log = Logger.getLogger(GradeDAOTest.class);
	
	private static final long TEST_ASSESSMENT_ID = 2063;
	private static final double TEST_NEW_SCORE = 27.66;
	private static final double FLOATING_NUMBER_VARIANCE = .01;
	private static final int TEST_TRAINEE_ID = 5529;
	private static final int TEST_BATCH_ID = 2150;
	private static final int TEST_CATEGORY_ID = 12;
	private static final int TEST_TRAINER_ID = 1;
	private static final short TEST_ASSESSMENT_WEEK = 7;
	
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	GradeDAO gradeDAO;
	@Autowired
	private TraineeDAO traineeDAO;
	@Autowired
	private BatchDAO batchDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private AssessmentDAO assessmentDAO;
	@Autowired
	private TrainerDAO trainerDAO;
	
	
	@Test
	@Ignore
	public void findAllGradeTest() {
		List<Grade> test = gradeDAO.findAll();
		Assert.assertTrue(!test.isEmpty());
	}
	
	
	@Test
	public void getGradebyIdTest() {
		long l = 1077;
		Grade test = gradeDAO.findByGradeId(l);
		//System.out.println(test.getGradeId());
		Assert.assertEquals(test.getGradeId(), l);
	}
	
	
	@Test
	public void getGradebyAssessmentTest() {
		List<Grade> test = gradeDAO.findByAssessmentAssessmentId(2053L);
		for(int i = 0; i < test.size(); i++) {
			//System.out.println(test.get(i).getAssessment().getAssessmentId());
			Assert.assertTrue(test.get(i).getAssessment().getAssessmentId() == 2053L);
		}
	}
	
	@Test
	public void getGradebyTraineeIdTest() {
		List<Grade> test = gradeDAO.findByTraineeTraineeId(5354);
		Assert.assertTrue(!test.isEmpty());
		for(int i = 0; i < test.size(); i++) {
			//System.out.println(test.get(i).getAssessment().getAssessmentId());
			Assert.assertTrue(test.get(i).getTrainee().getTraineeId() == 5354);
			Assert.assertFalse(test.get(i).getTrainee().getTraineeId() == 5353);
		}
	}
	
	@Test
	public void getGradebyCategoryIdTest() {
		List<Grade> test = gradeDAO.findByCategoryId(1);
		//System.out.println("Number of grades in Category 1:" + test.size() );
		for(int i = 0; i < test.size(); i++) {
			Assert.assertTrue(test.get(i).getAssessment().getCategory().getCategoryId() == 1);
		}
		
	}
	
	
	@Test
	public void getGradebyBatchIdTest() {
		List<Grade> test = gradeDAO.findByBatchId(2200);
		//System.out.println("Number of batch in Category 1:" + test.size() );
		for(int i = 0; i < test.size(); i++) {
			Assert.assertTrue(test.get(i).getTrainee().getBatch().getBatchId() == 2200);
		}
		
	}
	
	@Test
	public void getGradebyTrainerIdTest() {
		List<Grade> test = gradeDAO.findByTrainerId(1);
		//System.out.println("Number of batch in Category 1:" + test.size() );
		for(int i = 0; i < test.size(); i++) {
			Assert.assertTrue(test.get(i).getTrainee().getBatch().getTrainer().getTrainerId() == 1);
		}
		
	}
	
	
	
	@Test
	public void getGradebyBatchAndWeekTest() {
		Short s = 1;

		List<Grade> test = gradeDAO.findByWeek(2200, s);
		//System.out.println("Number of batch in Category 1:" + test.size() );
		for(int i = 0; i < test.size(); i++) {
			Assert.assertTrue(test.get(i).getAssessment().getBatch().getBatchId() == 2200);
			Assert.assertTrue(test.get(i).getAssessment().getWeek() == (short)1);
		}
	}
	
	/**
	 * Test methods:
	 * Positive tests for creating grades
	 * @see com.revature.caliber.data.GradeDAO#save(Grade)
	 */
	@Test
	public void saveValidGrade(){
		log.trace("CREATING VALID GRADES");
		
		//dependencies
		Assessment assessment = assessmentDAO.findOne(TEST_ASSESSMENT_ID);
		Trainee trainee = traineeDAO.findOne(TEST_TRAINEE_ID);
		
		
		/*
		 * Positive tests. Change according to change in business rules
		 * Test edge cases such as 1 and 100. 
		 * If business rules allow 0, test 0. If not, test 0.01
		 * If business rules allow above 100, change tests to match.
		 * 
		 */
		
		Grade lowGrade = new Grade(assessment, trainee, new Date(), 1);
		gradeDAO.save(lowGrade);
		Grade highGrade = new Grade(assessment, trainee, new Date(), 100);
		gradeDAO.save(highGrade);

	}
	
	/**
	 * Test methods:
	 * Negative tests for creating grades
	 * Currently ignored because there are no dao-level validations
	 * @see com.revature.caliber.data.GradeDAO#save(Grade)
	 */
	@Test
	@Ignore
	public void saveInvalidGrades(){
		log.trace("CREATING INVALID GRADES");
		
		//dependencies
		Trainee trainee = traineeDAO.findOne(TEST_TRAINEE_ID);
		
		/*
		 * Negative tests. Test anything that isn't allowed by business rules
		 * 
		 */
		try{
			Grade noConstraintGrade = new Grade(null, null, null, -0.10);
			gradeDAO.save(noConstraintGrade);
			fail();
		} catch(ConstraintViolationException e){
			log.trace(e);
		}
		try{
			Grade noAssessmentGrade = new Grade(null, trainee, new Date(), 353291.3);
			gradeDAO.save(noAssessmentGrade);
			fail();
		} catch(ConstraintViolationException e){
			log.trace(e);
		}
	}
	
	/**
	 * Test methods:
	 * @see com.revature.caliber.data.GradeDAO#updateGrade(Grade)
	 */
	@Test
	public void updateGrade(){
		log.trace("UPDATING GRADES");
		
		//get grade
		Grade grade = gradeDAO.findAll().get(0);
		
		//make sure old and new are not the same
		assertNotEquals(TEST_NEW_SCORE, grade.getScore());
		
		//update
		grade.setScore(TEST_NEW_SCORE);
		gradeDAO.save(grade);
		
		//assert change persisted
		assertEquals(TEST_NEW_SCORE,grade.getScore(), FLOATING_NUMBER_VARIANCE);
		
	}
	
	/**
	 * Test methods:
	 * @see com.revature.caliber.data.GradeDAO#findAll()
	 */
	@Test
	public void findAll(){
		log.trace("GETTING ALL GRADES");
		List<Grade> grades = gradeDAO.findAll();
		assertTrue(!grades.isEmpty());
	}
	
	/**
	 * Test methods:
	 * @see com.revature.caliber.data.GradeDAO#findByAssessment(Long)
	 */
	@Test
	public void findByAssessment(){
		log.trace("GETTING GRADES FOR ASSESSMENT");
		
		//get assessment
		Assessment assessment = assessmentDAO.findOne(TEST_ASSESSMENT_ID);
		//find all grades for assessment
		List<Grade> grades = gradeDAO.findByAssessmentAssessmentId(assessment.getAssessmentId());
		
		//assert each grade is of assesssment
		for(Grade grade: grades){
			assertEquals(assessment, grade.getAssessment());
		}
	}
	
	/**
	 * Test methods:
	 * @see com.revature.caliber.data.GradeDAO#findByTrainee(Integer)
	 */
	@Test
	public void findByTrainee(){
		log.trace("GETTING GRADES FOR TRAINEE");
		
		//get trainee 
		Trainee trainee = traineeDAO.findOne(TEST_TRAINEE_ID);
		
		//find all grades for that trainee
		List<Grade> grades = gradeDAO.findByTraineeTraineeId(trainee.getTraineeId());
		
		//loop through and assert that each grade belongs to trainee
		for(Grade grade: grades){
			assertEquals(trainee, grade.getTrainee());
		}
	}
	
	/**
	 * Test methods:
	 * @see com.revature.caliber.data.GradeDAO#findByBatch(Integer)
	 */
	@Test
	public void findByBatch(){
		log.trace("GETTING GRADES FOR BATCH");
		
		//get batch
		Batch batch = batchDAO.findOne(TEST_BATCH_ID);
		
		//find all grades for batch
		List<Grade> grades = gradeDAO.findByBatchId(batch.getBatchId());
		
		//assert that each grade belongs to batch

		for(Grade grade : grades){
			assertEquals(batch, grade.getTrainee().getBatch());
		}
	
	}
	
	/**
	 * Test methods:
	 * @see com.revature.caliber.data.GradeDAO#findByCategory(Integer)
	 */
	@Test
	public void findByCategory(){
		log.trace("GETTING GRADES FOR CATEGORY");
		
		//get category
		Category category = categoryDAO.findOne(TEST_CATEGORY_ID);
		
		//get all grades of category
		List<Grade> grades = gradeDAO.findByCategoryId(category.getCategoryId());
		
		//assert that each grade belongs to category
		for(Grade grade: grades){
			assertEquals(category, grade.getAssessment().getCategory());
		}

	}
	
	/**
	 * Test methods:
	 * @see com.revature.caliber.data.GradeDAO#findByWeek(Integer, Integer)
	 */
	@Test
	public void findByWeek(){	
		log.trace("GETTING GRADES FOR WEEK AND BATCH");
		
		//get list
		List<Grade> grades = gradeDAO.findByWeek(TEST_BATCH_ID, TEST_ASSESSMENT_WEEK);
		
		//assert that grades were for an assessment created for week and for batch
		for(Grade grade: grades){
			assertEquals(TEST_ASSESSMENT_WEEK, grade.getAssessment().getWeek());
			assertEquals(TEST_BATCH_ID, grade.getAssessment().getBatch().getBatchId());
		}
	}
	
	/**
	 * Test methods:
	 * @see com.revature.caliber.data.GradeDAO#findByTrainer(Integer)
	 */
	@Test 
	public void findByTrainer(){
		log.trace("GETTING GRADES FOR TRAINER");
		
		//get trainer
		Trainer trainer = trainerDAO.findOne(TEST_TRAINER_ID);
		//get grades for trainer
		List<Grade> grades = gradeDAO.findByTrainerId(trainer.getTrainerId());
		
		//assert grade belongs to trainer
		for(Grade grade: grades){
			assertEquals(trainer, grade.getTrainee().getBatch().getTrainer());
		}

		
	}
	
}
