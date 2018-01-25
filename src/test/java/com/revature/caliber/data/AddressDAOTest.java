package com.revature.caliber.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Address;
@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressDAOTest {
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	AddressDAO dao;
	@Test
	public void test() {
		List<Address> test = dao.findAll();
		assertFalse(test.isEmpty());
	}

}
