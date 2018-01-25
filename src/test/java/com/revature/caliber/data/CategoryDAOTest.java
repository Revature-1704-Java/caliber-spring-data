package com.revature.caliber.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
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
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	private static final Logger log = Logger.getLogger(CategoryDAO.class);
	
	private static final String CATEGORY_COUNT = "select count(category_id) from caliber_category";
	private static final String ACTIVE_CATEGORY = "select count(category_id) from caliber_category WHERE IS_ACTIVE = 1;";
	
	@Test
	public void testAll() {
		int expected = dao.findAllActive().size();
		int actual = jdbcTemplate.queryForObject(CATEGORY_COUNT, Integer.class);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testAllActive() {
		log.info("Testing findAllActive from CategoryDAO");
		int expected = dao.findAllActive().size();
		int actual = jdbcTemplate.queryForObject(ACTIVE_CATEGORY,Integer.class);
		assertEquals(expected, actual);
	}
	
	@Test
	public void findOne() {
		log.info("Testing findOne method from CategoryDAO");
		Category myCat = dao.findOne(1);
		assertNotNull(myCat);
		assertTrue(dao.findOne(1) instanceof Category);
		assertEquals(dao.findOne(1).toString(), "Java");
	}
	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.CategoryDAO.update()
	 */
	@Test
	public void update() {
		log.info("Testing update from CategoryDAO");
		String skillName = "Revature Pro";
		Category myCat = dao.findOne(1);
		myCat.setSkillCategory(skillName);
		dao.save(myCat);
		assertEquals(skillName, myCat.getSkillCategory());
	}
	/**
	 * Tests methods:
	 * @see com.revature.caliber.data.CategoryDAO.save()
	 */
	@Test
	public void save() {
		log.info("Testing save method from CategoryDAO");
		Category newCat = new Category("Underwater Basket Weaving", false);
		Long before = jdbcTemplate.queryForObject(CATEGORY_COUNT, Long.class);
		dao.save(newCat);
		Long after = jdbcTemplate.queryForObject(CATEGORY_COUNT, Long.class);
		assertEquals(++before, after);
	}
}
