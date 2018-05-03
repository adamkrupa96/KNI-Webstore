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

	private static int NAME_MIN = 2;
	private static int NAME_MAX = 32;
	private static String ONLY_DIGIT_ERROR_MSG = "Nazwa kategorii nie może składać się wyłącznie z liczb."; 
	private static String SIZE_ERROR_MSG = "Nazwa kategorii powinna mieć min. " + NAME_MIN + " znaki, a max. " + NAME_MAX + " znaki."; 
	private static String EXIST_ERROR_MSG = "Istnieje juz kategoria o podanej nazwię."; 
	
	@Autowired
	private CategoryService catService;

	@Override
	public void validate(Object objCategoryToValid, Errors errors) {
		if (objCategoryToValid == null) {
			errors.reject("null_error");
			return;
		}
		
		Category categoryToValidate = null;
		if ( !supports(objCategoryToValid.getClass()) ) {
			errors.reject("type_error");
			return;
		}			

		categoryToValidate = (Category) objCategoryToValid;
		if (isNameOrSubCategoriesNull(categoryToValidate)) {
			errors.reject("null_error");
			return;
		}
		
		String categoryName = categoryToValidate.getName();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty_error");
		rejectIfHasOnlyNumbers(errors, categoryName);
		rejectIfNameHasIncorrectLength(errors, categoryName);
		rejectIfCategoryExists(errors, categoryName);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Category.class);
	}
	
	private boolean isNameOrSubCategoriesNull(Category categoryToValidate) {
		return categoryToValidate.getName() == null || categoryToValidate.getSubCategories() == null;
	}
	
	private void rejectIfHasOnlyNumbers(Errors errors, String categoryName) {
		if (hasOnlyNumbers(categoryName))
			errors.rejectValue("name", "only_digits_error", ONLY_DIGIT_ERROR_MSG);
	}
	
	private boolean hasOnlyNumbers(String name) {
		return name.matches("[0-9]+");
	}
	
	private void rejectIfNameHasIncorrectLength(Errors errors, String name) {
		if ( name.length() < NAME_MIN || name.length() > NAME_MAX )
			errors.rejectValue("name", "size_error", SIZE_ERROR_MSG);
	}
	
	private void rejectIfCategoryExists(Errors errors, String name) {
		List<Category> allCategories = catService.getAllCategories();
		
		boolean anyMatch = allCategories.stream().anyMatch(eachCategory -> {
			String eachCategoryName = eachCategory.getName().trim().toUpperCase();
			return eachCategoryName.equals(name.trim().toUpperCase());
		});
		
		if (anyMatch)
			errors.rejectValue("name", "exists_error", EXIST_ERROR_MSG);
	}
}
