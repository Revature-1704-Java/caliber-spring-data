package com.revature.caliber.data;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Batch;
import com.revature.caliber.beans.Trainee;
import com.revature.caliber.beans.Trainer;
import com.revature.caliber.beans.TrainerRole;
import com.revature.caliber.beans.TrainingStatus;
@RunWith(SpringRunner.class)
@DataJpaTest
public class BatchDAOTest {
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	private BatchDAO dao;
	
	@Test
	public void BatchDAO_findOne(){
		Batch test1 = new Batch("Java", new Trainer("Sideshow Bob", "Clown", "killbart@kill", TrainerRole.ROLE_TRAINER ),new Date((long)1513054800000.0), new Date((long) 1516895781338.0), "Reston");
		int id=(int) entityManager.persistAndGetId(test1);
		Trainee subject =new Trainee("Bart Simpson", "resourceId", "String@email", test1);
		Trainee subject2 =new Trainee("Lisa Simpson", "resourceId", "String@email2", test1);
		subject.setTrainingStatus(TrainingStatus.Dropped);
		test1.getTrainees().add(subject);
		test1.getTrainees().add(subject2);
		entityManager.flush();
		Batch get= dao.findOne(id);
		System.out.println(get);
		boolean Nodrop=get.getTrainees().stream().map(
				t->t.getTrainingStatus()!=TrainingStatus.Dropped)
				.reduce(true,(acc,curr)->acc&&curr);
		Assert.assertThat(get,isA(Batch.class));
		assertTrue(get.getBatchId()==id);
		assertTrue(Nodrop);
	}
	@Test
	public void BatchDAO_findOneWithTraineesAndGrades(){
		
		Batch get= dao.findOne(2201);
		boolean grade_over_zero=get.getTrainees().stream().map(
				x->x.getGrades().stream().map(
						g->g.getScore()>0.0)
				.reduce(true,(ga,gc)->ga&&gc))
				.reduce(true, (acc,curr)->acc&&curr);
		boolean Nodrop=get.getTrainees().stream().map(
				t->t.getTrainingStatus()!=TrainingStatus.Dropped)
				.reduce(true,(acc,curr)->acc&&curr);
		Assert.assertThat(get,isA(Batch.class));
		assertTrue(get.getBatchId()==2201);
		assertTrue(grade_over_zero);
		assertTrue(Nodrop);
	}
	@Test
	public void BatchDAO_findAll(){
		List<Batch> test=dao.findAll();
		boolean Nodrop=test.stream()
				.map(b -> b.getTrainees().stream().map(t->t.getTrainingStatus()!=TrainingStatus.Dropped)
						.reduce(true, (acc,curr)->acc&&curr)).reduce(true,(a,b)->a&&b);
//		for(int i=0;i<test.size();i++){
//			System.out.println("new batch");
//			test.get(i).getTrainees().forEach(x->System.out.println(x.getTrainingStatus()));
//		}
		assertTrue(Nodrop);
		assertEquals(13, test.size());
	}
	@Test
	public void BatchDAO_findAllByTrainerOrCoTrainerId(){
		List<Batch> test=dao.findAllByTrainer(1);
		boolean Nodrop=test.stream()
				.map(b -> b.getTrainees().stream().map(t->t.getTrainingStatus()!=TrainingStatus.Dropped)
						.reduce(true, (acc,curr)->acc&&curr)).reduce(true,(a,b)->a&&b);
		assertTrue(Nodrop);
		assertEquals(6,test.size());
	}
	@Test
	public void BatchDAO_delete(){
		Trainer trainer1= new Trainer("Sideshow Bob", "Clown", "killbart@kill", TrainerRole.ROLE_TRAINER );
		entityManager.persist(trainer1);
		Batch test1 = new Batch("Java", trainer1 ,new Date((long)1513054800000.0), new Date((long) 1516895781338.0), "Reston");
		int id=(int) entityManager.persistAndGetId(test1);
		entityManager.flush();
		Batch newTest = entityManager.find(Batch.class, id);
		dao.delete(newTest);
		assertThat(entityManager.find(Batch.class, id), nullValue());
	}
	@Test
	public void BatchDAO_update(){
		Trainer trainer1= new Trainer("Sideshow Bob", "Clown", "killbart@kill", TrainerRole.ROLE_TRAINER );
		entityManager.persist(trainer1);
		entityManager.flush();
		Batch testBatch = dao.findOne(2050);
		testBatch.setTrainer(trainer1);
		Batch updated = dao.save(testBatch);
		assertThat(updated, equalTo(entityManager.find(Batch.class, 2050)));
		assertEquals(updated.getBatchId(),2050);
	}
	@Test
	public void BatchDAO_findAllCurrent(){
		ZonedDateTime now =ZonedDateTime.now();
		ZonedDateTime aMonthAgo= now.minusMonths(1);
		List<Batch> test = dao.findAllCurrent();
		boolean Nodrop=test.stream()
				.map(b -> b.getTrainees().stream().map(t->t.getTrainingStatus()!=TrainingStatus.Dropped)
						.reduce(true, (acc,curr)->acc&&curr)).reduce(true,(a,b)->a&&b);
		boolean active=test.stream().map(
				x->x.getEndDate().after(Date.from(now.toInstant()))
				&&x.getStartDate().after(Date.from(aMonthAgo.toInstant()))
				).reduce(true,(acc,curr)->acc&&curr);
		test.forEach(x->System.out.println(x.getStartDate()));
		assertTrue(active);
		assertTrue(Nodrop);
	}
	@Test
	public void BatchDAO_findAllCurrentWithTrainerId(){
		ZonedDateTime now =ZonedDateTime.now();
		ZonedDateTime aMonthAgo= now.minusMonths(1);
		List<Batch> test = dao.findAllCurrent(1);
		boolean Nodrop=test.stream()
				.map(b -> b.getTrainees().stream().map(t->t.getTrainingStatus()!=TrainingStatus.Dropped)
						.reduce(true, (acc,curr)->acc&&curr)).reduce(true,(a,b)->a&&b);
		boolean active=test.stream().map(
				x->x.getEndDate().after(Date.from(now.toInstant()))
				&&x.getStartDate().after(Date.from(aMonthAgo.toInstant()))
				).reduce(true,(acc,curr)->acc&&curr);
		assertTrue(active);
		assertTrue(Nodrop);
	}
	@Test
	public void BatchDAO_findAllCurrentWithNotesAndTrainees(){
		ZonedDateTime now =ZonedDateTime.now();
		ZonedDateTime aMonthAgo= now.minusMonths(1);
		List<Batch> test = dao.findAllCurrentWithNotesAndTrainees();
		boolean qcFeedback=test.stream().map(
				x->x.getNotes().stream().map(n->n.isQcFeedback()).reduce(true,(a,b)->a&&b))
				.reduce(true, (acc,curr)->acc&&curr);
		boolean active=test.stream().map(
				x->x.getEndDate().after(Date.from(now.toInstant()))
				&&x.getStartDate().after(Date.from(aMonthAgo.toInstant()))
				).reduce(true,(acc,curr)->acc&&curr);
		assertTrue(active);
		assertTrue(qcFeedback);
	}
	@Test
	public void BatchDAO_findAllCurrentWithNotes(){
		ZonedDateTime now =ZonedDateTime.now();
		ZonedDateTime aMonthAgo= now.minusMonths(1);
		List<Batch> test = dao.findAllCurrentWithNotes();
		boolean qcFeedback=test.stream().map(
				x->x.getNotes().stream().map(n->n.isQcFeedback()).reduce(true,(a,b)->a&&b))
				.reduce(true, (acc,curr)->acc&&curr);
		boolean active=test.stream().map(
				x->x.getEndDate().after(Date.from(now.toInstant()))
				&&x.getStartDate().after(Date.from(aMonthAgo.toInstant()))
				).reduce(true,(acc,curr)->acc&&curr);
		assertTrue(active);
		assertTrue(qcFeedback);
	}
	@Test
	public void BatchDAO_findAllCurrentWithTrainee(){
		ZonedDateTime now =ZonedDateTime.now();
		ZonedDateTime aMonthAgo= now.minusMonths(1);
		List<Batch> test = dao.findAllCurrentWithTrainees();
		boolean active=test.stream().map(
				x->x.getEndDate().after(Date.from(now.toInstant()))
				&&x.getStartDate().after(Date.from(aMonthAgo.toInstant()))
				).reduce(true,(acc,curr)->acc&&curr);
		boolean Nodrop=test.stream()
				.map(b -> b.getTrainees().stream().map(t->t.getTrainingStatus()!=TrainingStatus.Dropped)
						.reduce(true, (acc,curr)->acc&&curr)).reduce(true,(a,b)->a&&b);
		boolean grade_over_zero=test.stream()
				.map(b -> b.getTrainees().stream().map(
						t->t.getGrades().stream().map(
							g->g.getScore()>0.0).reduce(true,(sa,s)->sa&&s))
						.reduce(true, (acc,curr)->acc&&curr))
				.reduce(true,(a,b)->a&&b);
		assertTrue(grade_over_zero);
		assertTrue(Nodrop);
		assertTrue(active);
	}
	@Test
	public void BatchDAO_findAllAfterDate(){
		List<Batch> test = dao.findAllAfterDate(1, 1, 2017);
		Date date=Date.from(LocalDate.of(2017, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		boolean After = test.stream().map(x->x.getStartDate().after(date)).reduce(true,(acc,curr)->acc&&curr);
		boolean Nodrop=test.stream()
				.map(b -> b.getTrainees().stream().map(t->t.getTrainingStatus()!=TrainingStatus.Dropped)
						.reduce(true, (acc,curr)->acc&&curr)).reduce(true,(a,b)->a&&b);
		boolean grade_over_zero=test.stream()
				.map(b -> b.getTrainees().stream().map(
						t->t.getGrades().stream().map(
							g->g.getScore()>0.0).reduce(true,(sa,s)->sa&&s))
						.reduce(true, (acc,curr)->acc&&curr))
				.reduce(true,(a,b)->a&&b);
		assertTrue(grade_over_zero);
		assertTrue(Nodrop);
		assertTrue(After);
	}
}
