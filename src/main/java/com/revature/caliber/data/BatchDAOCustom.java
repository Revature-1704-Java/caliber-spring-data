package com.revature.caliber.data;

import java.util.List;

import com.revature.caliber.beans.Batch;

public interface BatchDAOCustom {
	public Batch findOne(int batchId);
	public Batch findOneWithTraineesAndGrades(int batchId);
	public List<Batch>findAllCurrent();
	public List<Batch>findAllCurrent(int trainerId);
	public List<Batch> findAllCurrentWithNotesAndTrainees();
	public List<Batch> findAllCurrentWithNotes();
	public List<Batch> findAllCurrentWithTrainees();
	public List<Batch> findAllAfterDate(int month, int day, int year);
}
