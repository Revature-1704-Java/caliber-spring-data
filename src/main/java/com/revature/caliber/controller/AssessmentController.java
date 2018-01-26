package com.revature.caliber.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@CrossOrigin
@RequestMapping(value = "/AssessmentController")
public class AssessmentController {

	@Autowired
	AssessmentDAO dao;

	/**
	 * User gets all assessment objects from table
	 *
	 * @param assessmentId
	 * @return assessmentList
	 */
	@GetMapping(value = "/getAssessments", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Assessment> getAssessmentes() {
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

	/**
	 * FIND ASSESSMENT BY WEEK
	 * 
	 * @param batchId
	 * @param week
	 * @return
	 */
	@GetMapping(value = "/getAssessment/{batchId}/{week}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Assessment>> findAssessmentByWeek(@PathVariable Integer batchId,
			@PathVariable Short week) {
		List<Assessment> assessments = dao.findByBatchIdAndWeek(batchId, week);
		if (assessments.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assessments, HttpStatus.OK);
	}

	// /**
	// * User provides all values for an assessment except for id#, new assessment
	// is
	// * set to values provided and saved to table
	// *
	// * @param street,
	// * city, state, zipcode, company, active
	// */
	// @GetMapping(value = "/addAssessment", produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// public void add_assessment(@RequestParam(value = "street", required = true)
	// String street,
	// @RequestParam(value = "city", required = true) String city,
	// @RequestParam(value = "state", required = true) String state,
	// @RequestParam(value = "zipcode", required = true) String zipcode,
	// @RequestParam(value = "company", required = true) String company,
	// @RequestParam(value = "active", required = true) boolean active) {
	//
	// Assessment assessment = new Assessment();
	//
	// assessment.setStreet(street);
	// assessment.setCity(city);
	// assessment.setState(state);
	// assessment.setZipcode(zipcode);
	// assessment.setCompany(company);
	// assessment.setActive(active);
	//
	// dao.save(assessment);
	// }
	//
	// /**
	// * User provides values, assessment is set to values provided and saved to
	// table
	// *
	// * @param street,
	// * city, state, zipcode, company, active
	// */
	// @GetMapping(value = "/updateAssessment", produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// public void update_assessment(@RequestParam(value = "assessmentId", required
	// = true) int assessmentId,
	// @RequestParam(value = "street", required = false) String street,
	// @RequestParam(value = "city", required = false) String city,
	// @RequestParam(value = "state", required = false) String state,
	// @RequestParam(value = "zipcode", required = false) String zipcode,
	// @RequestParam(value = "company", required = false) String company,
	// @RequestParam(value = "active", required = false) Boolean active) {
	//
	// Assessment assessment = dao.findByAssessmentId(assessmentId);
	//
	// if (street != null)
	// assessment.setStreet(street);
	// if (city != null)
	// assessment.setCity(city);
	// if (state != null)
	// assessment.setState(state);
	// if (zipcode != null)
	// assessment.setZipcode(zipcode);
	// if (company != null)
	// assessment.setCompany(company);
	// if (active != null)
	// assessment.setActive(active);
	//
	// dao.save(assessment);
	// }

	/**
	 * User provides id# assessment table is searched, delete assessment object if
	 * found
	 *
	 * @param assessmentId
	 */
	@GetMapping(value = "/deleteAssessment", produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete_assessment(@RequestParam(value = "assessmentId", required = true) int assessmentId) {
		Assessment assessment = dao.findByAssessmentId(assessmentId);
		dao.delete(assessment);
	}

}