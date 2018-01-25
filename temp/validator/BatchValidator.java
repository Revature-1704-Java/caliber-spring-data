package com.revature.caliber.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.revature.caliber.beans.Batch;

@Component
public class BatchValidator implements ConstraintValidator<ValidBatch, Batch> {

	@Override
	public void initialize(ValidBatch constraint) {
		// unused lifecycle method
	}

	@Override
	public boolean isValid(Batch batch, ConstraintValidatorContext ctx) {
		if (batch.getEndDate().before(batch.getStartDate())
				|| batch.getBorderlineGradeThreshold() > batch.getGoodGradeThreshold()
				|| batch.getTrainer().equals(batch.getCoTrainer())) {
			return false;
		}
		return true;
	}
}
