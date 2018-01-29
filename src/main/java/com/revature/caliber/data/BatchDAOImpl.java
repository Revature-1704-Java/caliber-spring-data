package com.revature.caliber.data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.revature.caliber.beans.Batch;
import com.revature.caliber.beans.TrainingStatus;

@Repository
public class BatchDAOImpl implements BatchDAOCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	private ZonedDateTime now =ZonedDateTime.now();
	private ZonedDateTime aMonthAgo= now.minusDays(30);
	@Override
	public List<Batch> findAllCurrent() {
		System.out.println(Date.from(now.toInstant()).toString());
		System.out.println(Date.from(aMonthAgo.toInstant()).toString());
		return entityManager.createQuery("select distinct b from Batch b left join fetch b.trainees t "
				+ "where b.startDate between :aMonthAgo and :now "
//				+ "where b.startDate <= :now "
				+ "and b.endDate >= :now "
				+ "and t.trainingStatus <> com.revature.caliber.beans.TrainingStatus.Dropped", Batch.class)
				.setParameter("aMonthAgo", Date.from(aMonthAgo.toInstant()), TemporalType.DATE)
				.setParameter("now", Date.from(now.toInstant()), TemporalType.DATE)
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
	@Override
	public Batch findOne(int batchId) {
		Batch b=entityManager.createQuery("select distinct b from Batch b left join fetch b.trainees t where "
				+ "b.batchId= :id", Batch.class)
				.setParameter("id", batchId)
				.getSingleResult();
		b.setTrainees(b.getTrainees().stream().filter(
				x->x.getTrainingStatus()!=TrainingStatus.Dropped)
				.collect(Collectors.toSet()));
		return b;
	}
	@Override
	public Batch findOneWithTraineesAndGrades(int batchId) {
		Batch b=entityManager.createQuery("select distinct b from Batch b left join fetch b.trainees t "
				+ "left join t.grades g "
				+ "where b.batchId= :id", Batch.class)
				.setParameter("id", batchId)
				.getSingleResult();
		b.setTrainees(b.getTrainees().stream().filter(
				x->x.getTrainingStatus()!=TrainingStatus.Dropped)
				.collect(Collectors.toSet()));
		
		b.getTrainees().forEach(t->t.setGrades(t.getGrades().stream().filter(g->g.getScore()>0.0).collect(Collectors.toSet())));
		return b;
	}
	@Override
	public List<Batch> findAllAfterDate(int month, int day, int year) {
		Date date = null;
		try {
			date=Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
		}catch(Exception e) {
			return new ArrayList<Batch>();
		}
		return entityManager.createQuery("select distinct b from Batch b "
				+ "left join fetch b.trainees t "
				+ "left join fetch t.grades g "
				+ "where b.startDate >= :date "
				+ "and t.trainingStatus <> com.revature.caliber.beans.TrainingStatus.Dropped "
				+ "and g.score > 0.0", Batch.class)
				.setParameter("date", date, TemporalType.DATE)
				.getResultList();
	}
}
