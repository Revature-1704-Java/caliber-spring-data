package com.revature.caliber.data;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.revature.caliber.beans.Batch;

@Repository
public class BatchDAOImpl implements BatchDAOCustom {
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<Batch> getAllCurrent() {
		ZonedDateTime now =ZonedDateTime.now();
		ZonedDateTime aMonthAgo= now.minusMonths(1);
		return entityManager.createQuery("select b from Batch b where b.startDate between :startDate and :endDate and b.endDate >= :endDate", Batch.class)
				.setParameter("startDate", Date.from(aMonthAgo.toInstant()), TemporalType.DATE)
				.setParameter("endDate", Date.from(now.toInstant()), TemporalType.DATE)
				.getResultList();
	}

}
