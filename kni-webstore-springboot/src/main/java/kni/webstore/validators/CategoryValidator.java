package kni.webstore.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(this.getClass());
	}

	@Override
	public void validate(Object categoryToValidate, Errors errors) {
		// TODO
	}

}
