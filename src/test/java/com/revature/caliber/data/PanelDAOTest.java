package com.revature.caliber.data;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.revature.caliber.beans.Panel;
import com.revature.caliber.beans.Trainee;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PanelDAOTest {

	@Autowired
	private PanelDAO panelDAO;

	/* Tests getting all panels */
	@Test
	public void findAllTest() {
		List<Panel> panels = panelDAO.findAll();
		System.out.println(panels);
		assertFalse(panels.isEmpty());
	}
	
	@Test
	public void findByTraineeTraineeIdTest() {
		List<Panel> panels = panelDAO.findByTraineeTraineeId(5501);
		System.out.println(panels);
		assertFalse(panels.isEmpty());
	}
	
	@Test
	public void findAllRepanelsTest() {
		List<Panel> panels = panelDAO.findAllRepanels();
		System.out.println(panels);
		assertFalse(panels.isEmpty());		
	}
	
	@Test
	public void findBiWeeklyPanelsTest() {
		List<Panel> panels = panelDAO.findBiWeeklyPanels();
		System.out.println(panels);
		assertTrue(panels.isEmpty()); /* DATA SET HAS NO PANELS WITHIN LAST 14 DAYS */
	}

//	@Test
//	public void saveTest() {
//		 Panel onePanel = [Panel [id=1, trainee=Trainee [traineeId=5500, name=Laut, Daniel, email=dlaut1@hotmail.com, trainingStatus=Dropped, major=null], panelist=Trainer [trainerId=19, name=Stanley Medikonda, title=Staging Manager, email=stanleym@revature.com, tier=ROLE_STAGING], interviewDate=2017-10-28, duration=1hr 30mins, format=Skype, internet=Stable, panelRound=1, recordingConsent=true, recordingLink=http://www.revature.com, status=Pass, feedback=[PanelFeedback [id=1, technology=Java, status=Pass, result=10, comment=Pretty good use of technical terms], PanelFeedback [id=2, technology=SQL, status=Pass, result=7, comment=Pretty good use of technical terms]], associateIntro=Good intro, projectOneDescription=ERS rocks, projectTwoDescription=Nice design discussion, projectThreeDescription=Caliber is the best, communicationSkills=Good communication, overall=Good work]//		System.out.println(panel);
//		assertFalse(panel == null);
//	}

	
	@Test
	public void findOneTest() {
		Panel panel = panelDAO.findOne(5);
		System.out.println(panel);
		assertFalse(panel == null);
	}
	
//	@Test
//	public void updateTest() {
//		List<Panel> panels = panelDAO.update();
//		System.out.println(panels);
//		assertFalse(panels.isEmpty()); 
//	}
	
	@Test
	public void deleteTest() {
		Panel panel = panelDAO.delete(5);
		System.out.println(panel);
		assertTrue(panel == null); 
	}
	
	@Test
	public void findAllTraineesAndPanelsByBatchTest() {
		List<Trainee> panels = panelDAO.findAllTraineesAndPanelsByBatch(2150);
		System.out.println(panels);
		assertFalse(panels.isEmpty()); 
	}
	
}
