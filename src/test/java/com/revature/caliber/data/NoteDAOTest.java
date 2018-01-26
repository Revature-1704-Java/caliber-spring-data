package com.revature.caliber.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.beans.Note;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteDAOTest {
	
	private static final int TEST_TRAINEE_ID = 5529;
	private static final int TEST_BATCH_ID = 2100;
	private static final int TEST_QCTRAINEE_ID = 5532;
	private static final int TEST_QCBATCH_ID = 2201;
	
	@Autowired
	private NoteDAO noteDao;

	/**
	 * Positive testing for finding a trainee note
	 * @see com.revature.caliber.data.NoteDAO#findByTraineeTraineeIdAndWeek(Integer, Short)
	 */
	@Test
	public void findByTraineeTraineeIdAndWeekTest(){
//		log.trace("Testing findTraineeNote");
		final short weekNum = 2;
		final String content = "Good communication. Not as confident and less use of technical terms";
		
		// find trainee note
		Note actual = noteDao.findTraineeNote(TEST_TRAINEE_ID, weekNum);

		// compare with expected
		assertFalse(actual.isQcFeedback());
		assertEquals(content, actual.getContent());
		assertEquals(weekNum, actual.getWeek());
		assertEquals(TEST_TRAINEE_ID, actual.getTrainee().getTraineeId());
	}

}
