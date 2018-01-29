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
import org.springframework.web.bind.annotation.RestController;

import com.revature.caliber.beans.Trainer;
import com.revature.caliber.data.TrainerDAO;

@RestController
@CrossOrigin
public class TrainerController {
	
	private static final Logger log = Logger.getLogger(TrainerController.class);
	
	@Autowired
	TrainerDAO dao;

	/**
	 * Gets all Trainers from database
	 *
	 * @param trainerId
	 * @return trainerList
	 */
	@GetMapping(value = "/trainer", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Trainer> getTrainers() {
		log.info("Getting all trainers");
		List<Trainer> trainerList = dao.findAll();
		return trainerList;
	}

	/**
	 * Get Trainer from database using provided id#, return object if found
	 *
	 * @param trainerId
	 * @return trainer object
	 */
	@GetMapping(value = "/trainer/{trainerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Trainer getTrainer(@PathVariable int trainerId) {
		log.info("Getting trainer with id: " + trainerId);
		Trainer trainer = dao.findByTrainerId(trainerId);
		return trainer;
	}
	
	/**
	 * Create new Trainer in database
	 * 
	 * @param trainer
	 * @return the response entity
	 */
	@RequestMapping(value = "/trainer/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// @PreAuthorize("hasAnyRole('VP', 'TRAINER')")
	public ResponseEntity<Void> createTrainer(@Valid @RequestBody Trainer trainer) {
		log.info("Creating trainer: " + trainer);
		dao.save(trainer);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * Update Trainer response entity.
	 *
	 * @param trainer
	 * 
	 * @return the response entity
	 */
	@RequestMapping(value = "/trainer/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// @PreAuthorize("hasAnyRole('VP', 'TRAINER')")
	public ResponseEntity<Trainer> updateTrainer(@Valid @RequestBody Trainer trainer) {
		log.info("Updating trainer: " + trainer);
		dao.save(trainer);
		return new ResponseEntity<>(trainer, HttpStatus.OK);
	}

	/**
	 * Delete Trainer from database using provided id#
	 *
	 * @param id
	 * 
	 * @return the response entity
	 */
	@RequestMapping(value = "/trainer/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// @PreAuthorize("hasAnyRole('VP', 'TRAINER')")
	public ResponseEntity<Void> deleteTrainer(@PathVariable int id) {
		log.info("Deleting trainer: " + id);
		Trainer trainer = new Trainer();
		trainer.setTrainerId(id);
		dao.delete(trainer);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
