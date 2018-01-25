package com.revature.caliber.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Panel;
import com.revature.caliber.beans.PanelFeedback;
import com.revature.caliber.beans.PanelStatus;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public interface PanelFeedbackDAO extends JpaRepository<PanelFeedback, Integer>{
	
	/*
	 * findAll distinct
	 * findAllForPanel(int panelId) distinct
	 * findFailedFeedbackByPanel(Panel panel) get panel id search by, status by repanel distinct, PanelStatus.Repanel
	 * save -auto
	 * findOne(long panelFeedbackId) uniqueResult()
	 * update -auto
	 * delete - auto
	 */
	@Query("SELECT DISTINCT pf FROM PanelFeedback pf")
	List<PanelFeedback> findAll();
	
	@Query("SELECT DISTINCT pf FROM PanelFeedback pf WHERE pf.panel.id= ?1")
	List<PanelFeedback> findAllForPanel(int panelId);
	
	@Query("SELECT DISTINCT pf FROM PanelFeedback pf WHERE pf.panel.id= ?1 and pf.status='Repanel'")
	List<PanelFeedback> findFailedFeedbackByPanel(Panel panel);
	
	//@Query()
	//PanelFeedback findOne(long panelFeedbackId);

}
