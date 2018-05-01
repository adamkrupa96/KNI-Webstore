package kni.webstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kni.webstore.model.Category;
import kni.webstore.model.SubCategory;
import kni.webstore.service.CategoryService;
import kni.webstore.validators.CategoryValidator;
import kni.webstore.validators.SubCategoryValidator;

@RestController
public class CategoryController {

	@Autowired
	private CategoryService catService;

	@Autowired
	private CategoryValidator catValidator;

	@Autowired
	private SubCategoryValidator subCatValidator;

	@GetMapping("/api/categories")
	public List<Category> getAllCategories() {
		return catService.getAllCategories();
	}

	@GetMapping("/api/categories/{id}")
	public Category getCategoryById(@PathVariable("id") Long id) {
		return catService.getCategoryById(id);
	}

	@GetMapping("/api/categories/byname/{name}")
	public Category getCategoryByName(@PathVariable("name") String name) {
		return catService.getCategoryByName(name);
	}

	@GetMapping("/api/categories/{id}/subcategories")
	public List<SubCategory> getSubCategoriesOfCategory(@PathVariable("id") Long id) {
		return catService.getCategoryById(id).getSubCategories();
	}

	@GetMapping("/api/categories/subcategories/{name}")
	public SubCategory getSubCategoryByName(@PathVariable("name") String name) {
		return catService.getSubCategoryByName(name);
	}

	@GetMapping("/api/categories/exist/{name}")
	public Boolean categoryExists(@PathVariable("name") String name) {
		return catService.categoryExist(name);
	}

	@PostMapping("/api/categories")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Category addCategory(@RequestBody Category category, BindingResult validationErrors) throws BindException {
		catValidator.validate(category, validationErrors);

		if (!validationErrors.hasErrors())
			return catService.addCategory(category);
		else
			throw new BindException(validationErrors);
	}

	@PostMapping("/api/categories/{id}/subcategories")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public SubCategory addSubCategoryOfCategory(@RequestBody SubCategory subCategory, @PathVariable("id") Long id,
			BindingResult validationErrors) throws BindException {
		Category categoryOfSubCategory = catService.getCategoryById(id);

		if (categoryOfSubCategory == null) {
			validationErrors.reject("category_not_found");
			throw new BindException(validationErrors);
		}

		subCategory.setCategory(categoryOfSubCategory);
		subCatValidator.validate(subCategory, validationErrors);

		if (!validationErrors.hasErrors())
			return catService.addSubCategory(categoryOfSubCategory, subCategory);
		else
			throw new BindException(validationErrors);
	}

	@PutMapping("/api/categories/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Category updateCategory(@RequestBody Category updatedCategory, @PathVariable("id") Long id,
			BindingResult validationErrors) throws BindException {
		catValidator.validate(updatedCategory, validationErrors);

		if (!validationErrors.hasErrors())
			return catService.updateCategory(id, updatedCategory);
		else
			throw new BindException(validationErrors);
	}

	@PutMapping("/api/categories/{catId}/subcategories/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public SubCategory updateSubCategory(@RequestBody SubCategory subCategory, @PathVariable("catId") Long catId,
			@PathVariable("id") Long id, BindingResult validationErrors) throws BindException {
		Category categoryOfSubCategory = catService.getCategoryById(catId);

		if (categoryOfSubCategory == null) {
			validationErrors.reject("category_not_found");
			throw new BindException(validationErrors);
		}

		subCategory.setCategory(categoryOfSubCategory);
		subCatValidator.validate(subCategory, validationErrors);

		if (!validationErrors.hasErrors())
			return catService.updateSubCategoryOfCategory(catId, subCategory);
		else
			throw new BindException(validationErrors);
	}

	@DeleteMapping("/api/categories/{id}")
	public void deleteCategory(@PathVariable("id") Long id) {
		catService.deleteCategoryById(id);
	}

	@DeleteMapping("/api/categories/subcategories/{id}")
	public void deleteSubCategory(@PathVariable("id") Long id) {
		catService.deleteSubCategoryById(id);
	}
}
