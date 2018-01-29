package com.revature.caliber.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Address;
@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressDAOTest {
	
	
	@Autowired
	AddressDAO dao;
	
	@Test
	public void testFindAll() {
		List<Address> test = dao.findAll();
		assertFalse(test.isEmpty());
	}
	
	@Test
	public void testFindByAddressId() {
		Address test = dao.findByAddressId(1);
		assertNotNull(test);
	}
	
	@Test
	public void testAddAddress() {
		Address test = new Address();
		
		test.setStreet("test");
		test.setCity("test");
		test.setState("test");
		test.setZipcode("test");
		test.setCompany("test");
		test.setActive(false);
		
		Address steve = dao.save(test);
		
		assertEquals(test.getStreet(), steve.getStreet());
	}
	
	@Test
	public void testUpdateAddress() {
		Address test = new Address();
		
		test.setStreet("test");
		test.setCity("test");
		test.setState("test");
		test.setZipcode("test");
		test.setCompany("test");
		test.setActive(false);
		
		dao.save(test);
		
		test = dao.findByAddressId(1);
		
		test.setStreet("test");
		test.setCity("test");
		test.setState("test");
		test.setZipcode("test");
		test.setCompany("test");
		test.setActive(false);
		
		dao.save(test);
		
		assertEquals(test, dao.findByAddressId(1));
	}
	

	@Test
	public void testDeleteAddress() {
		Address test = new Address();
		
		test.setStreet("test");
		test.setCity("test");
		test.setState("test");
		test.setZipcode("test");
		test.setCompany("test");
		test.setActive(false);
		
		Address steve = dao.save(test);
		dao.delete(steve);
		
		assertNull(dao.findByAddressId(steve.getAddressId()));
	}

}
