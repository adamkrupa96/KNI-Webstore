package kni.webstore.validators;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kni.webstore.model.Category;
import kni.webstore.model.SubCategory;

@Component
public class SubCategoryValidator implements Validator {

	private static int NAME_MIN = 2;
	private static int NAME_MAX = 32;
	private static String EXIST_ERROR_MSG = "Podkategoria o podanej nazwię już istnieje w tej kategorii.";
	private static String SIZE_ERROR_MSG = "Długość nazwy podkategorii min. "+ NAME_MIN +" znaki, max. "+ NAME_MAX +" znaki";
	private static String ONLY_DIGITS_ERROR_MSG = "Nazwa podkategorii nie może składać się wyłącznie z liczb";

	@Override
	public void validate(Object objSubCategory, Errors errors) {
		if (objSubCategory == null) {
			errors.reject("null_error");
			return;
		}

		SubCategory subCategoryToValidate = null;
		if (!supports(objSubCategory.getClass())) {
			errors.reject("type_error");
			return;
		}
		
		subCategoryToValidate = (SubCategory) objSubCategory;

		String subCategoryName = subCategoryToValidate.getName();
		if (isNameOrProductsNull(errors, subCategoryToValidate)) {
			errors.reject("null_error");
			return;
		}
		
		rejectIfHasOnlyNumbers(errors, subCategoryName);
		rejectIfHasIncorrectLength(errors, subCategoryName);
		rejectIfAlreadyExists(errors, subCategoryToValidate);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(SubCategory.class);
	}

	private boolean isNameOrProductsNull(Errors errors, SubCategory subCategory) {
		return subCategory.getName() == null || subCategory.getProducts() == null;
	}
	
	private void rejectIfHasOnlyNumbers(Errors errors, String subCategoryName) {
		if (hasOnlyNumbers(subCategoryName))
			errors.rejectValue("name", "only_digits_error", ONLY_DIGITS_ERROR_MSG);
	}

	private void rejectIfHasIncorrectLength(Errors errors, String subCategoryName) {
		if ( !hasGoodLength(subCategoryName) )
			errors.rejectValue("name", "size_error", SIZE_ERROR_MSG);
	}
	
	private boolean hasOnlyNumbers(String name) {
		return name.matches("[0-9]+");
	}

	private boolean hasGoodLength(String name) {
		return name.length() >= NAME_MIN && name.length() <= NAME_MAX;
	}

	private void rejectIfAlreadyExists(Errors errors, SubCategory subCategory) {
		if (alreadyExists(subCategory))
			errors.rejectValue("name", "exists_error", EXIST_ERROR_MSG);
	}
	
	private boolean alreadyExists(SubCategory subCategory) {
		Category categoryOfSubCategory = subCategory.getCategory();
		List<SubCategory> subCategoriesOfCategory = categoryOfSubCategory.getSubCategories();
		
		return subCategoriesOfCategory.stream().anyMatch(eachSubCategory -> {
			String eachSubCatNameTrim = eachSubCategory.getName().trim();
			String validationSubCatNameTrim = subCategory.getName().trim();
			
			return eachSubCatNameTrim.equalsIgnoreCase(validationSubCatNameTrim);
		});
	}

}
