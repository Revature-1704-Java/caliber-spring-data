package com.revature.caliber.data;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
import com.revature.caliber.beans.Trainer;
import com.revature.caliber.beans.TrainerRole;
@RunWith(SpringRunner.class)
@DataJpaTest
public class BatchDAOTest {
	private static final Date Date = null;
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	private BatchDAO dao;
	
	@Test
	public void BatchDAO_findbyBatchId(){
		Batch test1 = new Batch("Java", new Trainer("Sideshow Bob", "Clown", "killbart@kill", TrainerRole.ROLE_TRAINER ),new Date((long)1513054800000.0), new Date((long) 1516895781338.0), "Reston");
		int id=(int) entityManager.persistAndGetId(test1);
		entityManager.flush();
		
		Batch get= dao.findOne(id);
		Assert.assertThat(get,isA(Batch.class));
		assertTrue(get.getBatchId()==id);
	}
}
