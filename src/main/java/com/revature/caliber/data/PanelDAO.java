package com.revature.caliber.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Panel;
import com.revature.caliber.beans.Trainee;

/* @author Michael Manzanares */

@Repository
@Transactional
public interface PanelDAO extends JpaRepository<Panel, Integer> {
	
	/* find all panels 
	 * Useful for listing available panels */
	List<Panel> findAll();
	
	/* find by trainee id 
	 * User is the trainee? 
	 * the old panel dao orders the panels by interview date here. but isnt each trainee only have one panel at a time? */
	List<Panel> findByTraineeTraineeId(Integer traineeId);
	
	/* find by repanels ordered descending by interview date*/
	@Query("SELECT P FROM Panel P WHERE P.status = com.revature.caliber.beans.PanelStatus.Repanel ORDER BY P.interviewDate DESC")
	List<Panel> findAllRepanels();

	/* find panels in the last 14 days NO DATA TO TEST ON */
	@Query("FROM Panel p WHERE p.interviewDate >= TRUNC(SYSDATE) - 13")
	List<Panel> findRecentPanels();
	
	/* Convenience method 
	 * save a panel */
//	Panel save(Panel panel);
	
	/* find by panel id */
	Panel findOne(int id);
	
	/* update panel */
//	Panel update(Panel panel);
	
	/* Convenience method 
	 * delete a panel by panel id */
	Panel delete(int id);
	
	/* find all trainees and panels */
	@Query("SELECT T FROM Trainee T LEFT JOIN FETCH T.panelInterviews P WHERE T.batch.batchId = ?1 AND T.trainingStatus <> com.revature.caliber.beans.TrainingStatus.Dropped")
	List<Trainee> findAllTraineesAndPanelsByBatch(int batchId);	
	
}
