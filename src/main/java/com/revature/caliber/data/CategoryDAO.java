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

	@Query(value="SELECT * FROM CALIBER_CATEGORY ORDER BY CALIBER_CATEGORY.CATEGORY_ID ASC", nativeQuery=true)
	List<Category> findAll();
	
	@Query(value="SELECT * FROM CALIBER_CATEGORY WHERE CALIBER_CATEGORY.IS_ACTIVE = 1 ORDER BY CALIBER_CATEGORY.SKILL_CATEGORY ASC", nativeQuery=true)
	List<Category> findAllActive();
}
