package com.revature.caliber.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Panel;
import com.revature.caliber.beans.Trainee;

/**
 * @author Michael Manzanares 
 */

@Repository
@Transactional
public interface PanelDAO extends JpaRepository<Panel, Integer> {

	/** find by trainee id 
	 * @param traineeID the traineeId that identifies the trainee
	 * @return a list of panels associated with the trainee
	 */
	List<Panel> findByTraineeTraineeId(Integer traineeId);
	
	/** find by repanels ordered descending by interview date
	 * @return a list of panels that have a status of Repanel
	 */
	@Query("SELECT P FROM Panel P WHERE P.status = com.revature.caliber.beans.PanelStatus.Repanel ORDER BY P.interviewDate DESC")
	List<Panel> findAllRepanels();

	/** find panels in the last 14 days NO DATA TO TEST ON
	 * @return a list of panels that happen in the last 14 days
	 */
	@Query("FROM Panel p WHERE p.interviewDate >= TRUNC(SYSDATE) - 13")
	List<Panel> findRecentPanels();
	
	/** find all trainees and panels
	 * @param batchId
	 * @return a list of trainees and panels by batch id
	 */
	@Query("SELECT T FROM Trainee T LEFT JOIN FETCH T.panelInterviews P WHERE T.batch.batchId = ?1 AND T.trainingStatus <> com.revature.caliber.beans.TrainingStatus.Dropped")
	List<Trainee> findAllTraineesAndPanelsByBatch(int batchId);	
	
	/** find all panels 
	 * Useful for listing available panels 
	 * @return a list of all panels
	 */
	List<Panel> findAll();
	
	/** find by panel id
	 * @param id
	 * @return a panel found with the id parameter
	 */
	Panel findOne(int id);
	
	/** Convenience method 
	 * save a panel 
	 * @return a new panel 
	 */
	@SuppressWarnings("unchecked")
	Panel save(Panel panel);
	
	/** Convenience method 
	 * delete a panel by panel id
	 * @return a panel that was deleted by panel id
	 */
	Panel delete(int id);
}
