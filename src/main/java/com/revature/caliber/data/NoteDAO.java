/**
 * 
 */
package com.revature.caliber.data;

import java.util.List;

import org.h2.command.dml.Update;
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
	 * @return The list of batch-level Notes written by Trainers for a given week
	 */
	@Query("select distinct n from Note n left join n.batch b left join n.trainee t where b.batchId=?1 and t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and n.batch.batchId=?1 and n.week=?2 and n.qcFeedback=false and n.type=com.revature.caliber.beans.NoteType.BATCH")
	List<Note> findBatchNotes(Integer batchId, Short week);
	
	
	/**
	 * Returns a list of Notes for a Trainee identified by traineeId
	 * Spring Data version of: com.revature.caliber.data.NoteDAO.findAllPublicIndividualNotes()
	 * 
	 * @param traineeId The traineeId that identifies the Trainee
	 * @return the list of Notes for a Trainee identified by traineeId
	 */
	@Query("select distinct n from Note n left join n.batch where n.trainee.traineeId=?1 and n.trainee.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped and n.type=com.revature.caliber.beans.NoteType.TRAINEE")
	List<Note> findAllPublicIndividualNotes(Integer traineeId);
	
	/**
	 * Returns the note for a Trainee identified by traineeId for a specified week.
	 * Additionally, the NoteType should be TRAINEE and the qcFeedback should be false.
	 * 
	 * @param traineeId The traineeId that identifies the Trainee
	 * @param week The week for which the Trainee's note will be retrieved
	 * @return A note for a Trainee identified by traineeId for a specified week
	 */
	@Query("select distinct n from Note n where n.trainee.traineeId=?1 and n.week=?2 and n.type=com.revature.caliber.beans.NoteType.TRAINEE and n.qcFeedback=false")
	Note findTraineeNote(Integer traineeId, Short week);
	
}
