package com.revature.caliber.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Trainer;
import com.revature.caliber.beans.TrainerRole;

@Repository
public interface TrainerDAO extends JpaRepository<Trainer, Integer> {

	static final Logger log = Logger.getLogger(TrainerDAO.class);
	//SessionFactory sessionFactory;

	/**
	 * Find a trainer by their email address. Practical for authenticating users
	 * through SSO
	 * 
	 * @param email
	 * @return
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public Trainer findByEmail(String email);

	/**
	 * Find trainer by the given identifier
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public Trainer findById(Integer trainerId);

}
