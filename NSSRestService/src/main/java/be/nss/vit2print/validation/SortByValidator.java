package be.nss.vit2print.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SortByValidator implements
		ConstraintValidator<SortByValidation, String> {

	@Override
	public void initialize(SortByValidation constraint) {
		// Default implementation
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return AssetSearchSortBy.sortByTypes().contains(value);
	}
}
