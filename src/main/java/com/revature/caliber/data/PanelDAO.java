package com.revature.caliber.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Panel;

@Repository
@Transactional
public interface PanelDAO extends JpaRepository<Panel, Integer> {
	List<Panel> findAll();
}
