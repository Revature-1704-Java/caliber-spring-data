package com.revature.caliber.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Panel;
import com.revature.caliber.beans.PanelStatus;
import com.revature.caliber.beans.Trainee;
import com.revature.caliber.beans.TrainingStatus;

/**
 * @author Connor Monson
 * @author Matt 'Spring Data' Prass
 */

@Repository
public interface PanelDAO extends JpaRepository<Panel, Integer> {

	static final Logger log = Logger.getLogger(PanelDAO.class);
	static final String INTERVIEW_DATE = "interviewDate";
	//SessionFactory sessionFactory;
	
	/**
	 * Looks for all panels where the user was the trainee
	 * 
	 * @param trainee
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Panel> findByTrainee(Integer traineeId);
	
}
