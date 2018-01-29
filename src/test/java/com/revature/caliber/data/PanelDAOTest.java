package com.revature.caliber.data;

import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.InterviewFormat;
import com.revature.caliber.beans.Panel;
import com.revature.caliber.beans.PanelStatus;
import com.revature.caliber.beans.Trainee;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PanelDAOTest {

	@Autowired
	private PanelDAO panelDAO;

	Panel panel;

	@Before
	public void initialize() {
		panel = new Panel();
		panel.setFormat(InterviewFormat.Phone);
		panel.setPanelRound(1);
		panel.setStatus(PanelStatus.Pass);
//		panel.setTrainee(traineeDao.findOne(1));
//		panel.setPanelist(trainerDao.findOne(3));
		panel.setInterviewDate(new Date());
}
	
	/* Tests getting all panels */
	@Test
	public void findAllTest() {
		List<Panel> panels = panelDAO.findAll();
		System.out.println(panels);
		assertFalse(panels.isEmpty());
	}
	
	/* Tests getting all panels belonging to a trainee */
	@Test
	public void findByTraineeTraineeIdTest() {
		List<Panel> panels = panelDAO.findByTraineeTraineeId(5501);
		System.out.println(panels);
		assertFalse(panels.isEmpty());
	}
	
	/* Tests getting all repanels */
	@Test
	public void findAllRepanelsTest() {
		List<Panel> panels = panelDAO.findAllRepanels();
		System.out.println(panels);
		assertFalse(panels.isEmpty());		
	}
	
	/* Tests getting all panels within the last 14 days */
	@Test
	public void findRecentPanelsTest() {
		List<Panel> panels = panelDAO.findRecentPanels();
		System.out.println(panels);
		assertTrue(panels.isEmpty()); /* DATA SET HAS NO PANELS WITHIN LAST 14 DAYS */
	}

	/* Tests saving a panel */
//	@Test
//	public void saveTest() {
//		Panel onePanel = getPanel();
//		Panel panel = panelDAO.save(onePanel);
//		System.out.println(panels);
//		assertFalse(panel == null);
//	}

	/* Tests getting one panel by panel Id */
	@Test
	public void findOneTest() {
		Panel panel = panelDAO.findOne(5);
		System.out.println(panel);
		assertFalse(panel == null);
	}
	
	/* Tests updating a panel by panel */
//	@Test
//	public void updateTest() {
//		Panel onePanel = getPanel();
//		List<Panel> panels = panelDAO.update(onePanel);
//		System.out.println(panels);
//		assertFalse(panels.isEmpty()); 
//	}
	
	/* Tests deleting a panel by panel Id */
	@Test
	public void deleteTest() {
		Panel panel = panelDAO.delete(5);
		System.out.println(panel);
		assertTrue(panel == null); 
	}
	
	/* Tests getting Trainees and their panels by Batch Id */
	@Test
	public void findAllTraineesAndPanelsByBatchTest() {
		List<Trainee> panels = panelDAO.findAllTraineesAndPanelsByBatch(2150);
		System.out.println(panels);
		assertFalse(panels.isEmpty()); 
	}

}
