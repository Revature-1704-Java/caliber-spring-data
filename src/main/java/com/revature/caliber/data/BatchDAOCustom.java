package com.revature.caliber.data;

import java.util.List;

import com.revature.caliber.beans.Batch;

public interface BatchDAOCustom {
	public List<Batch>findAllCurrent();
	public List<Batch>findAllCurrent(int trainerId);
	public List<Batch> findAllCurrentWithNotesAndTrainees();
}
