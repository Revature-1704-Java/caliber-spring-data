package com.revature.caliber.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.revature.caliber.beans.Panel;

public class PanelDAOTest {
//	@Autowired
//    private TestEntityManager entityManager;
	@Autowired
	PanelDAO panelDao;
	@Test
	public void panelTest() {
		List<Panel> panels = panelDao.findAll();
//		System.out.println(panels);
		assertTrue(panels.isEmpty());
	}
}
