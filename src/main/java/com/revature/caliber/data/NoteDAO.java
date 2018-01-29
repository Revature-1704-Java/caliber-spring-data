/**
 * 
 */
package com.revature.caliber.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Note;

/**
 * @author Kei Peralta
 *
 */
@Repository
@Transactional
public interface NoteDAO extends JpaRepository<Note, Integer> {
	
	/**
	 * Saves a Note to the database
	 * @param note The note to save to the database
	 * @return The Note that was saved to the database 
	 */
	@SuppressWarnings("unchecked")
	Note save(Note note);
	
	/**
	 * Returns all Trainer-written, Batch-level Notes for a given week.
	 * @param batchId The batchId that identifies the Batch
	 * @param week The week for which the Batch's note will be retrieved
	 * @return The List of batch-level Notes written by Trainers for the given week
	 */
	@Query("select distinct n from Note n left join n.batch b left join n.trainee t where b.batchId=?1 and t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and n.batch.batchId=?1 and n.week=?2 and n.qcFeedback=false and n.type=com.revature.caliber.beans.NoteType.BATCH")
	List<Note> findBatchNotes(Integer batchId, Short week);
	
	
	/**
	 * Returns a list of Notes for a Trainee identified by traineeId
	 * Spring Data version of: com.revature.caliber.data.NoteDAO.findAllPublicIndividualNotes()
	 * 
	 * @param traineeId The traineeId that identifies the Trainee
	 * @return The List of Notes for the Trainee identified by traineeId
	 */
	@Query("select distinct n from Note n left join n.batch where n.trainee.traineeId=?1 and n.trainee.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and n.type=com.revature.caliber.beans.NoteType.TRAINEE order by n.week asc")
	List<Note> findAllPublicIndividualNotes(Integer traineeId);
	
	/**
	 * Returns the note for a Trainee identified by traineeId for a specified week.
	 * Additionally, the NoteType should be TRAINEE and the qcFeedback should be false.
	 * 
	 * @param traineeId The traineeId that identifies the Trainee
	 * @param week The week for which the Trainee's note will be retrieved
	 * @return A note for a Trainee identified by traineeId for the given week
	 */
	@Query("select distinct n from Note n where n.trainee.traineeId=?1 and n.week=?2 and n.type=com.revature.caliber.beans.NoteType.TRAINEE and n.qcFeedback=false")
	Note findTraineeNote(Integer traineeId, Short week);
	
	/**
	 * Returns Trainer-written Individual Notes for a Batch for a given week
	 * @param batchId The batchId that identifies the Batch
	 * @param week The week for which the Batch's Individual Notes will be retrieved
	 * @return The List of Trainer-written Individual Notes for the Batch for the given week
	 */
	@Query("select distinct n from Note n left join n.trainee t where t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and t.batch.batchId=?1 and n.week=?2 and n.type=com.revature.caliber.beans.NoteType.TRAINEE and n.qcFeedback=false")
	List<Note> findIndividualNotes(Integer batchId, Short week);
	
	/**
	 *  Returns the QC Note for a Trainee for a given week
	 *  @param traineeId The traineeId that identifies the Trainee
	 *  @param week The week for which a Trainee's QC Note will be retrieved
	 *  @return The QC Note for a Trainee for the given week
	 */
	@Query("select distinct n from Note n where n.trainee.traineeId=?1 and n.week=?2 and n.type=com.revature.caliber.beans.NoteType.QC_TRAINEE and n.qcFeedback=true")
	Note findQCTraineeNote(Integer traineeId, Short week);
	
	/**
	 * Returns the Batch Note for a Batch for a given week
	 * @param batchId The batchId that identifies the Batch
	 * @param week The week for which a Batch's Note will be retrieved
	 * @return The Batch Note for a Batch for the given week
	 */
	@Query("select distinct n from Note n left join n.batch.trainees t where t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and n.type=com.revature.caliber.beans.NoteType.QC_BATCH and n.batch.batchId=?1 and n.week=?2 and n.qcFeedback=true")
	Note findQCBatchNotes(Integer batchId, Short week);
	
	/**
	 * Returns all Batch-level QC Notes for a given Batch
	 * @param batchId The batchId that identifies the Batch
	 * @return The List of Batch-level QC Notes for the given Batch
	 */
	@Query("select distinct n from Note n left join n.batch.trainees t where t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and n.type=com.revature.caliber.beans.NoteType.QC_BATCH and n.batch.batchId=?1")
	List<Note> findAllBatchQCNotes(Integer batchId);
	
	/**
	 * Returns the QC Notes for a Trainee for a given week
	 * @param traineeId The traineeId that identifies the Trainee
	 * @param week The week for which a Trainee's QC Notes will be retrieved
	 * @return The List of QC Notes for a Trainee for the given week 
	 */
	@Query("select distinct n from Note n left join n.trainee t where t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and t.traineeId=?1 and n.week=?2 and n.type=com.revature.caliber.beans.NoteType.QC_TRAINEE and n.qcFeedback=true")
	List<Note> findQCIndividualNotes(Integer traineeId, Short week);
	
	/**
	 * Returns all Batch-level Notes for a given week
	 * @param batchId The batchId that identifies the Batch
	 * @param week The week for which a Batch's Notes will be retrieved
	 * @return The List of Batch-level Notes for the given week
	 */
	@Query("select distinct n from Note n left join n.batch.trainees t where t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and n.type=com.revature.caliber.beans.NoteType.BATCH and n.batch.batchId=?1 and n.week=?2")
	List<Note> findAllBatchNotes(Integer batchId, Short week);
	
	/**
	 * Returns all Individual Notes for a given week
	 * @param traineeId The traineeId that identifies the Trainee
	 * @param week The week for which an Individual's Note will be retrieved
	 * @return The List of Individual Notes for the given week
	 */
	@Query("select distinct n from Note n left join n.trainee t where t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and n.type=com.revature.caliber.beans.NoteType.TRAINEE and t.traineeId=?1 and n.week=?2")
	List<Note> findAllIndividualNotes(Integer traineeId, Short week);
	
	/**
	 * Returns all Batch-level QC Notes
	 * @param batchId The batchId that identifies the Batch
	 * @return The List of Batch-level QC Notes for the given Batch 
	 */
	@Query("select distinct n from Note n left join n.batch b left join b.trainees t where t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and b.batchId=?1 and n.qcFeedback=true and n.type=com.revature.caliber.beans.NoteType.QC_BATCH order by n.week asc")
	List<Note> findAllQCBatchNotes(Integer batchId);
	
	/**
	 * Returns all QC Notes for all Trainees in a Batch for a given week
	 * @param batchId The batchId that identifies the Batch
	 * @param week The week for which all QC Notes for all Trainees in the Batch will be retrieved
	 * @return The List of all QC Notes for all Trainees in the Batch for the given week 
	 */
	@Query("select distinct n from Note n left join n.trainee t where t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and t.batch.batchId=?1 and n.week=?2 and n.qcFeedback=true and n.type=com.revature.caliber.beans.NoteType.QC_TRAINEE order by n.week asc")
	List<Note> findAllQCTraineeNotes(Integer batchId, Short week);
	
	/**
	 * Returns all QC Notes for a Trainee in a Batch
	 * @param traineeId The traineeId that identifies the Trainee
	 * @return The List of all QC Notes for the given Trainee 
	 */
	@Query("select distinct n from  Note n left join n.trainee t where t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and t.traineeId=?1 and n.qcFeedback=true and n.type=com.revature.caliber.beans.NoteType.QC_TRAINEE order by n.week asc")
	List<Note> findAllQCTraineeOverallNotes(Integer traineeId);
}
