package com.revature.caliber.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.PanelFeedback;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PanelFeedbackDAOTest {
	
	private static final Logger log = Logger.getLogger(PanelFeedbackDAOTest.class);
	private static final String PANEL_FEEDBACK_COUNT = "SELECT count(panel_feedback_id) FROM caliber_panel_feedback";
	private static final String PANEL_FEEDBACK_COUNT_ID = PANEL_FEEDBACK_COUNT + " WHERE panel_id = ";

	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	PanelFeedbackDAO dao;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
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
	/*
	@Test
	public void findFailedFeedbackByPanelDAO() {
		// positive testing
		int panelId = 60;
		int actual = dao.findFailedFeedbackByPanel(panelDAO.findOne(panelId)).size();
		int expected = jdbcTemplate.queryForObject(PANEL_FEEDBACK_COUNT_ID + panelId + " AND panel_status = 'Repanel'",
				Integer.class);

		assertEquals(expected, actual);

		// negative testing
		panelId = -8309;
		actual = dao.findFailedFeedbackByPanel(panelDAO.findOne(panelId)).size();
		expected = jdbcTemplate.queryForObject(PANEL_FEEDBACK_COUNT_ID + panelId + " AND panel_status = 'Repanel'",
				Integer.class);

		assertEquals(expected, actual);
	}
	*/
}
