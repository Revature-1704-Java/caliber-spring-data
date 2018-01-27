package com.revature.caliber.data;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
		entityManager.flush();
		
		Batch get= dao.findOne(id);
		Assert.assertThat(get,isA(Batch.class));
		assertTrue(get.getBatchId()==id);
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
		boolean active=test.stream().map(
				x->x.getEndDate().after(Date.from(now.toInstant()))
				&&x.getStartDate().after(Date.from(aMonthAgo.toInstant()))
				).reduce(true,(acc,curr)->acc&&curr);
		assertTrue(active);
	}
	@Test
	public void BatchDAO_findAllCurrentWithId(){
		ZonedDateTime now =ZonedDateTime.now();
		ZonedDateTime aMonthAgo= now.minusMonths(1);
		List<Batch> test = dao.findAllCurrent(1);
		test.forEach(x->System.out.println(x.getTrainer().getTrainerId()));
		boolean active=test.stream().map(
				x->x.getEndDate().after(Date.from(now.toInstant()))
				&&x.getStartDate().after(Date.from(aMonthAgo.toInstant()))
				).reduce(true,(acc,curr)->acc&&curr);
		assertTrue(active);
	}
}
