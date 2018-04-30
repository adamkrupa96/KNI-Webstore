package kni.webstore.validators;

import kni.webstore.model.Category;
import kni.webstore.service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {

	private static String ONLY_DIGIT_ERROR_MSG = "Nazwa kategorii nie może składać się wyłącznie z liczb."; 
	private static String SIZE_ERROR_MSG = "Nazwa kategorii powinna mieć min. 2 znaki, a max. 32 znaki."; 
	private static String EXIST_ERROR_MSG = "Istnieje juz kategoria o podanej nazwię."; 
	
	@Autowired
	private CategoryService catService;

	public CategoryValidator() {
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Category.class);
	}

	@Override
	public void validate(Object objCategoryToValid, Errors errors) {
		Category categoryToValidate = null;
		
		if (objCategoryToValid == null) {
			errors.reject("null_error");
			return;
		}
		if ( !supports(objCategoryToValid.getClass()) ) {
			errors.reject("type_error");
			return;
		}			

		categoryToValidate = (Category) objCategoryToValid;
		if ( categoryToValidate.getName() == null || categoryToValidate.getSubCategories() == null ) {
			errors.reject("null_error");
			return;
		}
		
		String categoryName = categoryToValidate.getName();
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty_error");
		
		if ( hasOnlyNumbers(categoryName) )
			errors.rejectValue("name", "only_digits_error", ONLY_DIGIT_ERROR_MSG);
		
		if ( categoryName.length() < 2 || categoryName.length() > 32 ) {
			errors.rejectValue("name", "size_error", SIZE_ERROR_MSG);
			return;
		}

		List<Category> allCategories = catService.getAllCategories();
		
		boolean anyMatch = allCategories.stream()
				.anyMatch(eachCategory -> eachCategory.getName().equals(categoryName));
		
		if ( anyMatch ) errors.rejectValue("name", "exist_error", EXIST_ERROR_MSG);
	}
	
	private boolean hasOnlyNumbers(String name) {
		return name.matches("[0-9]+");
	}
}
