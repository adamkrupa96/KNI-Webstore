package kni.webstore.validators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kni.webstore.model.SubCategory;
import kni.webstore.service.CategoryService;

@Component
public class SubCategoryValidator implements Validator {

	private static String EXIST_ERROR_MSG = "Podkategoria o podanej nazwię już istnieje";
	private static String SIZE_ERROR_MSG = "Długość nazwy podkategorii min. 2 znaki, max. 32 znaki";
	private static String ONLY_DIGITS_ERROR_MSG = "Nazwa podkategorii nie może składać się wyłącznie z liczb";

	@Autowired
	private CategoryService catService;

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
		} else
			subCategoryToValidate = (SubCategory) objSubCategory;

		String subCategoryName = subCategoryToValidate.getName();
		if (subCategoryName == null || subCategoryToValidate.getProducts() == null) {
			errors.reject("null_error");
			return;
		}

		if (hasOnlyNumbers(subCategoryName))
			errors.rejectValue("name", "only_digits_error", ONLY_DIGITS_ERROR_MSG);

		if (!hasGoodLength(subCategoryName))
			errors.rejectValue("name", "size_error", SIZE_ERROR_MSG);

		if (alreadyExists(subCategoryName))
			errors.rejectValue("name", "exist_error", EXIST_ERROR_MSG);

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(SubCategory.class);
	}

	private boolean hasOnlyNumbers(String name) {
		return name.matches("[0-9]+");
	}

	private boolean hasGoodLength(String name) {
		return name.length() >= 2 && name.length() <= 32;
	}

	private boolean alreadyExists(String subCategoryName) {
		List<SubCategory> allSubCategories = catService.getAllSubCategories();
		final String subCategoryNameTrimmed = subCategoryName.trim().toUpperCase();

		return allSubCategories.stream().anyMatch(eachSubCategory -> {
			String eachSubCategoryName = eachSubCategory.getName().trim().toUpperCase();
			return eachSubCategoryName.equals(subCategoryName);
		});
	}

}
