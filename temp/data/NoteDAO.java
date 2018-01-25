package com.revature.caliber.data;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Note;
import com.revature.caliber.beans.NoteType;
import com.revature.caliber.beans.TrainingStatus;

/**
 * 
 * @author Patrick Walsh
 * @author Ateeb Khawaja
 *
 */
@Repository
public interface NoteDAO extends JpaRepository<Note, Integer> {

	static final Logger log = Logger.getLogger(NoteDAO.class);
	//SessionFactory sessionFactory;
	static final String BATCH = "batch";
	static final String B_TRAINEES = "b.trainees";
	static final String T_TRAINING_STATUS = "t.trainingStatus";
	static final String B_BATCH_ID = "b.batchId";
	static final String TRAINEE = "trainee";
	static final String T_TRAINEE_ID = "t.traineeId";
	static final String QC_FEEDBACK = "qcFeedback";
	static final int MONTHS_BACK = -1;
	static final String BATCH_BATCH_ID = "batch.batchId";

	/**
	 * Returns all notes for a particular trainee. Used for cumulative reporting
	 * on a single trainee. Only trainer notes are displayed.
	 * 
	 * @param traineeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Note> findByTraineeId(Integer traineeId);

	/**
	 * Returns all qc notes for a batch
	 * 
	 * @param batchId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Note> findByBatchId(Integer batchId);
	
}