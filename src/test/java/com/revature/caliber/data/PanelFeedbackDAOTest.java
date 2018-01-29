package com.revature.caliber.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Category;
import com.revature.caliber.beans.InterviewFormat;
import com.revature.caliber.beans.Panel;
import com.revature.caliber.beans.PanelFeedback;
import com.revature.caliber.beans.PanelStatus;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PanelFeedbackDAOTest {
	
	private static final Logger log = Logger.getLogger(PanelFeedbackDAOTest.class);
	private static final String PANEL_FEEDBACK_COUNT = "SELECT count(panel_feedback_id) FROM caliber_panel_feedback";
	private static final String PANEL_FEEDBACK_COUNT_ID = PANEL_FEEDBACK_COUNT + " WHERE panel_id = ";

	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	private PanelFeedbackDAO dao;
	@Autowired
	private CategoryDAO catDao;
	@Autowired
	private TraineeDAO traineeDao;
	@Autowired
	private TrainerDAO trainerDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void testFindAll() {
		log.info("Getting all feedback using PanelFeedbackDAO getAll function");
		long num = jdbcTemplate.queryForObject(PANEL_FEEDBACK_COUNT, Long.class);
		assertNotNull(dao.findAll());
		assertEquals(dao.findAll().size(), num);
	}
	
	@Test
	public void findAllForPanelDAO() {
		int panelId = 40;
		int actual = dao.findAllForPanel(panelId).size();
		int expected = jdbcTemplate.queryForObject(PANEL_FEEDBACK_COUNT_ID + panelId, Integer.class);

		assertEquals(expected, actual);

		panelId = -789;
		actual = dao.findAllForPanel(panelId).size();
		expected = jdbcTemplate.queryForObject(PANEL_FEEDBACK_COUNT_ID + panelId, Integer.class);
		assertEquals(expected, actual);
	}
	
	@Test
	public void saveFeedbackDAO() {
		log.info("Saving a new Feedback using PanelFeedbackDAO");
		int before = jdbcTemplate.queryForObject(PANEL_FEEDBACK_COUNT, Integer.class);
		PanelFeedback panelFeedback = new PanelFeedback();
		Panel panel = new Panel();
		panel.setFormat(InterviewFormat.Phone);
		panel.setPanelRound(1);
		panel.setStatus(PanelStatus.Pass);
		panel.setTrainee(traineeDao.findOne(1));
		panel.setPanelist(trainerDao.findOne(1));
		Category category = catDao.findOne(2);

		panelFeedback.setComment("test");
		panelFeedback.setResult(5);
		panelFeedback.setTechnology(category);
		panelFeedback.setStatus(PanelStatus.Pass);
		panelFeedback.setPanel(panel);

		dao.saveAndFlush(panelFeedback);
		long panelFeedbackid = panelFeedback.getId();
		log.info("panelFeedbackId: " + panelFeedbackid);
		int after = jdbcTemplate.queryForObject(PANEL_FEEDBACK_COUNT, Integer.class);
		assertEquals(panelFeedback.toString(), dao.findOne(panelFeedbackid).toString());
		assertEquals(++before, after);
	}
	
	
	@Test
	public void getFeedbackByIdDAO() {
		log.info("Finding feedback by panel id");
		long panelFId = 140;
		int expected = 70;
		System.out.println("zzz" +dao.findOne(panelFId));
		assertEquals(dao.findOne(panelFId).getPanel().getId(), expected);
	}
	
	
	@Test
	public void nullGetPanelFeedbackByInt() {
		log.info("Attempting to get a panel that doesn't exist");
		PanelFeedback feedback = dao.findOne((long) 99999999);
		assertNull(feedback);
	}
	
	@Test
	public void updateFeedbackDAO() {
		log.info("UpdateFeedbackDAO Test");
		String comment = "11111";
		long panelFId = 10;

		PanelFeedback actual = dao.findOne(panelFId);

		actual.setComment(comment);
		dao.save(actual);
		assertEquals(dao.findOne(panelFId).getComment(), actual.getComment());
	}
	
}
