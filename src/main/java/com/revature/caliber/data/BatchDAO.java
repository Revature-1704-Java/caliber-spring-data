package com.revature.caliber.data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.revature.caliber.beans.Batch;
import com.revature.caliber.beans.TrainingStatus;

@Repository
public interface BatchDAO extends JpaRepository<Batch, Integer>, BatchDAOCustom{
	static final Logger log = Logger.getLogger(BatchDAO.class);
	@Query("select distinct b from Batch b left join fetch b.trainees t where "
			+ "t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped "
			+ "or t.trainingStatus is null order by b.startDate desc")
	public List<Batch> findAll();
	@Query("select distinct b from Batch b where b.trainer.trainerId=:id "
			+ "or b.coTrainer.trainerId=:id order by b.startDate desc")
	public List<Batch>findAllByTrainer(@Param("id") int trainerId);
}
