/**
 * 
 */
package com.revature.caliber.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
	 * Returns a list of Notes for a Trainee specified by traineeId
	 * 
	 * @param traineeId The traineeId to identify the Trainee
	 * @return 
	 */
	List<Note> findByTraineeTraineeId(Integer traineeId);
}
