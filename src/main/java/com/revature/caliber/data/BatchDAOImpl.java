package com.revature.caliber.data;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.revature.caliber.beans.Batch;

@Repository
public class BatchDAOImpl implements BatchDAOCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	private ZonedDateTime now =ZonedDateTime.now();
	private ZonedDateTime aMonthAgo= now.minusMonths(1);
	@Override
	public List<Batch> findAllCurrent() {
		return entityManager.createQuery("select b from Batch b where b.startDate between :startDate and :endDate and b.endDate >= :endDate", Batch.class)
				.setParameter("startDate", Date.from(aMonthAgo.toInstant()), TemporalType.DATE)
				.setParameter("endDate", Date.from(now.toInstant()), TemporalType.DATE)
				.getResultList();
	}
	@Override
	public List<Batch> findAllCurrent(int trainerId) {
		CriteriaBuilder cb= entityManager.getCriteriaBuilder();
		CriteriaQuery<Batch> q=cb.createQuery(Batch.class);
		Root<Batch> b = q.from(Batch.class);
		q.select(b).where(
			cb.and(
				cb.between(b.get("startDate"), Date.from(aMonthAgo.toInstant()), Date.from(now.toInstant())),
				cb.greaterThan(b.get("endDate"), Date.from(now.toInstant())),
				cb.or(
					cb.equal(b.get("trainer").get("trainerId"),trainerId),
					cb.equal(b.get("coTrainer").get("trainerId"), trainerId)
				)
			)
		);
		
		return entityManager.createQuery(q).getResultList();
	}
	@Override
	public List<Batch> findAllCurrentWithNotesAndTrainees() {
		CriteriaBuilder cb= entityManager.getCriteriaBuilder();
		CriteriaQuery<Batch> q=cb.createQuery(Batch.class);
		Root<Batch> b = q.from(Batch.class);
		q.select(b).where(
			cb.and(
				cb.between(b.get("startDate"), Date.from(aMonthAgo.toInstant()), Date.from(now.toInstant())),
				cb.greaterThan(b.get("endDate"), Date.from(now.toInstant()))
			)
		);
		
		return entityManager.createQuery(q).getResultList();
	}

}
