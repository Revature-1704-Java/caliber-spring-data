package com.revature.caliber.data;

import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Grade;
@RunWith(SpringRunner.class)
@DataJpaTest

public class GradeDAOTest {
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	GradeDAO dao;
	@Test
	public void test() {
		List<Grade> test = dao.findAll();
		Assert.assertTrue(!test.isEmpty());
	}
}
