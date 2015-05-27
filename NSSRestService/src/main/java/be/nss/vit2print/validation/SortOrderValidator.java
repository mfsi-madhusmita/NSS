package be.nss.vit2print.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SortOrderValidator implements
		ConstraintValidator<SortOrderValidation, String> {

	@Override
	public void initialize(SortOrderValidation constraint) {
		// Default implementation
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return AssetSortOrder.sortByTypes().contains(value);
	}

}
