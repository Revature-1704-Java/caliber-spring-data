package com.revature.caliber.data;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Trainee;

@Repository
public interface TraineeDAO extends JpaRepository<Trainee, Integer> {
	
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Trainee> findAll();
	
	@Query("SELECT DISTINCT ct FROM Trainee ct WHERE ct.trainingStatus != com.revature.caliber.beans.TrainingStatus.Dropped") 
	public List<Trainee> findAllNotDropped();
	
	@Query("SELECT DISTINCT ct FROM Trainee ct left join ct.batch left join ct.grades g WHERE ct.batch.batchId = :bId AND g.score > 0.0 AND ct.trainingStatus != com.revature.caliber.beans.TrainingStatus.Dropped")
	public List<Trainee> findAllByBatch(@Param("bId") Integer bId);
	
	@Query("SELECT DISTINCT ct FROM Trainee ct left join ct.batch left join ct.grades WHERE ct.batch.batchId = :bId AND ct.trainingStatus = com.revature.caliber.beans.TrainingStatus.Dropped")
	public List<Trainee> findAllDroppedByBatch(@Param("bId") Integer bId);
	
	@Query("SELECT DISTINCT ct FROM Trainee ct left join ct.batch left join ct.grades left join ct.notes WHERE ct.batch.trainer.trainerId = :tId AND ct.trainingStatus != com.revature.caliber.beans.TrainingStatus.Dropped")
	public List<Trainee> findAllByTrainer(@Param("tId") Integer tId); 
	
	@Query("SELECT DISTINCT ct FROM Trainee ct left join fetch ct.batch left join fetch ct.grades WHERE ct.traineeId= :tId AND ct.trainingStatus != com.revature.caliber.beans.TrainingStatus.Dropped")
	public Trainee findOneTrainee(@Param("tId") Integer tId); 
	
	@Query("SELECT ct FROM Trainee ct WHERE ct.email LIKE %:eml% AND ct.trainingStatus != com.revature.caliber.beans.TrainingStatus.Dropped  ")
	public List<Trainee> findByEmail(@Param("eml") String email);
	
	@Query("SELECT ct FROM Trainee ct WHERE ct.name LIKE %:name% AND ct.trainingStatus != com.revature.caliber.beans.TrainingStatus.Dropped  ")
	public List<Trainee> findByName(@Param("name") String name);
	
	@Query("SELECT ct FROM Trainee ct WHERE ct.skypeId LIKE %:skypeid% AND ct.trainingStatus != com.revature.caliber.beans.TrainingStatus.Dropped  ")
	public List<Trainee> findBySkypeId(@Param("skypeid") String skypeid);

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public Trainee findByTraineeId(int id);
	
	
	
}
