package com.revature.caliber.data;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Address;
import com.revature.caliber.beans.Category;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryDAOTest {
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	CategoryDAO dao;
	
	@Test
	public void testAll() {
		List<Category> test = dao.findAll();
		Assert.assertFalse(test.isEmpty());
	}
	
	@Test
	public void testAllActive() {
		List<Category> test = dao.findAllActive();
		Assert.assertFalse(test.isEmpty());
	}
}
