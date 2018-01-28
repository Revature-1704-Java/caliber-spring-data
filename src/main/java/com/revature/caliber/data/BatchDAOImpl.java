package com.revature.caliber.data;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
		return entityManager.createQuery("select distinct b from Batch b left join fetch b.trainees t "
				+ "where b.startDate between :startDate and :endDate "
				+ "and b.endDate >= :endDate "
				+ "and t.trainingStatus <> com.revature.caliber.beans.TrainingStatus.Dropped", Batch.class)
				.setParameter("startDate", Date.from(aMonthAgo.toInstant()), TemporalType.DATE)
				.setParameter("endDate", Date.from(now.toInstant()), TemporalType.DATE)
				.getResultList();
	}
	@Override
	public List<Batch> findAllCurrent(int trainerId) {
		return entityManager.createQuery("select distinct b from Batch b left join fetch b.trainees t "
				+ "where b.startDate between :startDate and :endDate "
				+ "and b.endDate >= :endDate "
				+ "and b.trainer.trainerId = :trainerId "
				+ "and t.trainingStatus <> com.revature.caliber.beans.TrainingStatus.Dropped", Batch.class)
				.setParameter("startDate", Date.from(aMonthAgo.toInstant()), TemporalType.DATE)
				.setParameter("endDate", Date.from(now.toInstant()), TemporalType.DATE)
				.setParameter("trainerId", trainerId)
				.getResultList();
//		CriteriaBuilder cb= entityManager.getCriteriaBuilder();
//		CriteriaQuery<Batch> q=cb.createQuery(Batch.class);
//		Root<Batch> b = q.from(Batch.class);
//		Fetch<Batch,Trainee> ft= b.fetch("trainees", JoinType.LEFT);
//		Join<Batch, Trainee> jt= b.joinCollection("trainees", JoinType.LEFT);
//		q.select(b).where(
//			cb.and(
//				cb.between(b.get("startDate"), Date.from(aMonthAgo.toInstant()), Date.from(now.toInstant())),
//				cb.greaterThan(b.get("endDate"), Date.from(now.toInstant())),
//				cb.equal(jt.get("trainingStatus"), TrainingStatus.Dropped)
//			)
//		);
//		return entityManager.createQuery(q).getResultList();
	}
	@Override
	public List<Batch> findAllCurrentWithNotesAndTrainees() {
		return entityManager.createQuery("select distinct b from Batch b "
				+ "left join fetch b.trainees t "
				+ "left join fetch b.notes n "
				+ "left join t.grades g "
				+ "where b.startDate between :startDate and :endDate "
				+ "and b.endDate >= :endDate "
				+ "and t.trainingStatus <> com.revature.caliber.beans.TrainingStatus.Dropped "
				+ "and n.qcFeedback = true "
				+ "and g.score > 0.0", Batch.class)
				.setParameter("startDate", Date.from(aMonthAgo.toInstant()), TemporalType.DATE)
				.setParameter("endDate", Date.from(now.toInstant()), TemporalType.DATE)
				.getResultList();
	}
	@Override
	public List<Batch> findAllCurrentWithNotes() {
		return entityManager.createQuery("select distinct b from Batch b "
				+ "left join fetch b.trainees t "
				+ "left join fetch b.notes n "
				+ "where b.startDate between :startDate and :endDate "
				+ "and b.endDate >= :endDate "
				+ "and t.trainingStatus <> com.revature.caliber.beans.TrainingStatus.Dropped "
				+ "and n.qcFeedback = true ", Batch.class)
				.setParameter("startDate", Date.from(aMonthAgo.toInstant()), TemporalType.DATE)
				.setParameter("endDate", Date.from(now.toInstant()), TemporalType.DATE)
				.getResultList();
	}
	@Override
	public List<Batch> findAllCurrentWithTrainees() {
		return entityManager.createQuery("select distinct b from Batch b "
				+ "left join fetch b.trainees t "
				+ "left join t.grades g "
				+ "where b.startDate between :startDate and :endDate "
				+ "and b.endDate >= :endDate "
				+ "and t.trainingStatus <> com.revature.caliber.beans.TrainingStatus.Dropped "
				+ "and g.score > 0.0", Batch.class)
				.setParameter("startDate", Date.from(aMonthAgo.toInstant()), TemporalType.DATE)
				.setParameter("endDate", Date.from(now.toInstant()), TemporalType.DATE)
				.getResultList();
	}
}
