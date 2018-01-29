package com.revature.caliber.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Batch;

@Repository
public interface BatchDAO extends JpaRepository<Batch, Integer>, BatchDAOCustom{
	static final Logger log = Logger.getLogger(BatchDAO.class);
	@Query("select distinct b from Batch b left join fetch b.trainees t where "
			+ "t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped "
			+ "or t.trainingStatus is null order by b.startDate desc")
	public List<Batch> findAll();
	@Query("select distinct b from Batch b left join fetch b.trainees t "
			+ "where (b.trainer.trainerId = :id or b.coTrainer.trainerId = :id) "
			+ "and t.trainingStatus <> com.revature.caliber.beans.TrainingStatus.Dropped")
	public List<Batch> findAllByTrainer(@Param("id") int trainerId);
	@Query("select distinct b from Batch b left join fetch b.trainees t where "
			+ "b.batchId= :id ")
	public Batch findOneWithDroppedTrainees(@Param("id") int batchId );
	
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public <S extends Batch> S save(Batch batch);
}
