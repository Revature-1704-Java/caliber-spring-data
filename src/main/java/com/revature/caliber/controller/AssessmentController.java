package com.revature.caliber.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caliber.beans.Assessment;
import com.revature.caliber.data.AssessmentDAO;

/**
 * Used for assessment CRUD operations.
 * 
 * @author Matthew McCormick
 *
 */
@RestController
// @PreAuthorize("isAuthenticated()")
@CrossOrigin // (origins = "http://ec2-54-163-132-124.compute-1.amazonaws.com")
public class AssessmentController {

	private static final Logger log = Logger.getLogger(AssessmentController.class);

	@Autowired
	AssessmentDAO dao;

	/**
	 * QC can no longer create assessment, trainer only function Create assessment
	 * response entity.
	 *
	 * @param assessment
	 *            the assessment
	 * @return the response entity
	 */
	@RequestMapping(value = "/trainer/assessment/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// @PreAuthorize("hasAnyRole('VP', 'TRAINER')")
	public ResponseEntity<Void> createAssessment(@Valid @RequestBody Assessment assessment) {
		log.info("Creating assessment: " + assessment);
		dao.save(assessment);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * Delete assessment response entity.
	 *
	 * @param id
	 *            the id
	 * @return the response entity
	 */
	@RequestMapping(value = "/trainer/assessment/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// @PreAuthorize("hasAnyRole('VP', 'TRAINER')")
	public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
		log.info("Deleting assessment: " + id);
		Assessment assessment = new Assessment();
		assessment.setAssessmentId(id);
		dao.delete(assessment);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Update assessment response entity.
	 *
	 * @param assessment
	 *            the assessment
	 * @return the response entity
	 */
	@RequestMapping(value = "/trainer/assessment/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// @PreAuthorize("hasAnyRole('VP', 'TRAINER')")
	public ResponseEntity<Assessment> updateAssessment(@Valid @RequestBody Assessment assessment) {
		log.info("Updating assessment: " + assessment);
		dao.save(assessment);
		return new ResponseEntity<>(assessment, HttpStatus.OK);
	}

	/**
	 * FIND ASSESSMENT BY WEEK
	 * 
	 * @param batchId
	 * @param week
	 * @return
	 */
	@GetMapping(value = "/trainer/assessment/{batchId}/{week}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Assessment>> findAssessmentByWeek(@PathVariable Integer batchId,
			@PathVariable Short week) {
		log.debug("Find assessment by week number " + week + " for batch " + batchId + " ");
		List<Assessment> assessments = dao.findByBatchIdAndWeek(batchId, week);
		if (assessments.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assessments, HttpStatus.OK);
	}

	/**
	 * User gets all assessment objects from table
	 *
	 * @param assessmentId
	 * @return assessmentList
	 */
	@GetMapping(value = "/getAssessments", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Assessment> getAssessments() {
		List<Assessment> assessmentList = dao.findAll();
		return assessmentList;
	}

	/**
	 * User provides id# assessment table is searched, return object if found
	 *
	 * @param assessmentId
	 * @return assessment object
	 */
	@GetMapping(value = "/getAssessment", produces = MediaType.APPLICATION_JSON_VALUE)
	public Assessment getAssessment(@RequestParam(value = "assessmentId", required = true) int assessmentId) {
		Assessment assessment = dao.findByAssessmentId(assessmentId);
		return assessment;
	}

}