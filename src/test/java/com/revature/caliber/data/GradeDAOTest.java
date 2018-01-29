package com.revature.caliber.data;

import static org.junit.Assert.assertNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Grade;
@RunWith(SpringRunner.class)
@DataJpaTest

public class GradeDAOTest {
	
	
	private static final Logger log = Logger.getLogger(GradeDAOTest.class);
	
	private static final int TEST_ASSESSMENT_ID = 2063;
	private static final double TEST_NEW_SCORE = 27.66;
	private static final double FLOATING_NUMBER_VARIANCE = .01;
	private static final int TEST_TRAINEE_ID = 5529;
	private static final int TEST_BATCH_ID = 2150;
	private static final int TEST_CATEGORY_ID = 12;
	private static final int TEST_TRAINER_ID = 1;
	private static final int TEST_ASSESSMENT_WEEK = 7;
	
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	GradeDAO dao;
	//@Autowired
	//private TraineeDAO traineeDAO;
	//@Autowired
	//private BatchDAO batchDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private AssessmentDAO assessmentDAO;
	//@Autowired
	//private TrainerDAO trainerDAO;
	
	
	@Test
	public void findAllGradeTest() {
		List<Grade> test = dao.findAll();
		Assert.assertTrue(test.isEmpty());
	}
	
	
	@Test
	public void getGradebyIdTest() {
		long l = 1077;
		Grade test = dao.findByGradeId(l);
		//System.out.println(test.getGradeId());
		Assert.assertEquals(test.getGradeId(), l);
	}
	
	
	@Test
	public void getGradebyAssessmentTest() {
		List<Grade> test = dao.findByAssessmentAssessmentId(2053L);
		for(int i = 0; i < test.size(); i++) {
			//System.out.println(test.get(i).getAssessment().getAssessmentId());
			Assert.assertTrue(test.get(i).getAssessment().getAssessmentId() == 2053L);
		}
	}
	
	@Test
	public void getGradebyTraineeIdTest() {
		List<Grade> test = dao.findByTraineeTraineeId(5354);
		Assert.assertTrue(!test.isEmpty());
		for(int i = 0; i < test.size(); i++) {
			//System.out.println(test.get(i).getAssessment().getAssessmentId());
			Assert.assertTrue(test.get(i).getTrainee().getTraineeId() == 5354);
			Assert.assertFalse(test.get(i).getTrainee().getTraineeId() == 5353);
		}
	}
	
	@Test
	public void getGradebyCategoryIdTest() {
		List<Grade> test = dao.findByCategoryId(1);
		//System.out.println("Number of grades in Category 1:" + test.size() );
		for(int i = 0; i < test.size(); i++) {
			Assert.assertTrue(test.get(i).getAssessment().getCategory().getCategoryId() == 1);
		}
		
	}
	
	
	@Test
	public void getGradebyBatchIdTest() {
		List<Grade> test = dao.findByBatchId(2200);
		//System.out.println("Number of batch in Category 1:" + test.size() );
		for(int i = 0; i < test.size(); i++) {
			Assert.assertTrue(test.get(i).getTrainee().getBatch().getBatchId() == 2200);
		}
		
	}
	
	@Test
	public void getGradebyTrainerIdTest() {
		List<Grade> test = dao.findByTrainerId(1);
		//System.out.println("Number of batch in Category 1:" + test.size() );
		for(int i = 0; i < test.size(); i++) {
			Assert.assertTrue(test.get(i).getTrainee().getBatch().getTrainer().getTrainerId() == 1);
		}
		
	}
	
	
	
	@Test
	public void getGradebyBatchAndWeekTest() {
		Short s = 1;

		List<Grade> test = dao.findByWeek(2200, s);
		//System.out.println("Number of batch in Category 1:" + test.size() );
		for(int i = 0; i < test.size(); i++) {
			Assert.assertTrue(test.get(i).getAssessment().getBatch().getBatchId() == 2200);
			Assert.assertTrue(test.get(i).getAssessment().getWeek() == (short)1);
		}
	}
	
	
	
}
