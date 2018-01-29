package com.revature.caliber.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Category;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public interface CategoryDAO extends JpaRepository<Category, Integer>{

	@Query("SELECT DISTINCT c FROM Category c ORDER BY c.categoryId ASC")
	List<Category> findAll();
	
	@Query("SELECT DISTINCT c FROM Category c WHERE c.active=true ORDER BY c.skillCategory ASC")
	List<Category> findAllActive();
}
