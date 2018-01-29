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
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.revature.caliber.beans.InterviewFormat;
import com.revature.caliber.beans.Panel;
import com.revature.caliber.beans.PanelStatus;
import com.revature.caliber.beans.Trainee;
import com.revature.caliber.beans.Trainer;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PanelDAOTest {

	@Autowired
    private TestEntityManager entityManager;

	@Autowired
	private PanelDAO panelDAO;

	Trainee trainee;
	Trainer trainer;
	
	Panel panel;

	@Before
	public void initialize() {
		trainee = entityManager.find(Trainee.class, 1);
		trainer = entityManager.find(Trainer.class, 3);
		panel = new Panel();
		panel.setFormat(InterviewFormat.Phone);
		panel.setPanelRound(1);
		panel.setStatus(PanelStatus.Pass);
		panel.setTrainee(trainee);
		panel.setPanelist(trainer);
		panel.setInterviewDate(new Date());
}
	
	/** Tests getting all panels */
	@Test
	public void findAllTest() {
		List<Panel> panels = panelDAO.findAll();
		System.out.println(panels);
		assertFalse(panels.isEmpty());
	}
	
	/** Tests getting all panels belonging to a trainee */
	@Test
	public void findByTraineeTraineeIdTest() {
		List<Panel> panels = panelDAO.findByTraineeTraineeId(5501);
		System.out.println(panels);
		assertFalse(panels.isEmpty());
	}
	
	/** Tests getting all repanels */
	@Test
	public void findAllRepanelsTest() {
		List<Panel> panels = panelDAO.findAllRepanels();
		System.out.println(panels);
		assertFalse(panels.isEmpty());		
	}
	
	/** Tests getting all panels within the last 14 days */
	@Test
	public void findRecentPanelsTest() {
		List<Panel> panels = panelDAO.findRecentPanels();
		System.out.println(panels);
		assertTrue(panels.isEmpty()); /* DATA SET HAS NO PANELS WITHIN LAST 14 DAYS */
	}

	/** Tests saving a panel */
	@Test
	public void saveTest() {
		Panel onePanel = panel;
		Panel panel = panelDAO.save(onePanel);
		
		int id = panel.getId();
		Panel panelConfirm = entityManager.find(Panel.class, id);
		
		assertNotNull(panelConfirm);
		assertEquals(panelConfirm.getId(), id);
	}

	/** Tests getting one panel by panel Id */
	@Test
	public void findOneTest() {
		Panel panel = panelDAO.findOne(5);
		System.out.println(panel);
		assertFalse(panel == null);
	}
	
	/** Tests deleting a panel by panel Id */
	@Test
	public void deleteTest() {
		Panel panel = panelDAO.delete(5);
		System.out.println(panel);
		assertTrue(panel == null); 
	}
	
	/** Tests getting Trainees and their panels by Batch Id */
	@Test
	public void findAllTraineesAndPanelsByBatchTest() {
		List<Trainee> panels = panelDAO.findAllTraineesAndPanelsByBatch(2150);
		System.out.println(panels);
		assertFalse(panels.isEmpty()); 
	}
}
