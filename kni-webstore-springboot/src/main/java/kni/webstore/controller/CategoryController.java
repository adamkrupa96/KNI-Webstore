package kni.webstore.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RestController
public class CategoryController {

	@Autowired
	private CategoryService catService;
	
	@GetMapping("/api/categories")
	public Set<Category> getAllCategories() {
		return catService.getAllCategories();
	}
	
	@GetMapping("/api/categories/{id}")
	public Category getCategoryById(@PathVariable("id") Long id) {
		return catService.getCategoryById(id);
	}
	
	@GetMapping("/api/categories/{name}")
	public Category getCategoryByName(@PathVariable("name") String name) {
		return catService.getCategoryByName(name);
	}
	
	@GetMapping("/api/categories/{id}/subcategories")
	public Set<SubCategory> getSubCategoriesOfCategory(@PathVariable("id") Long id) {
		return catService.getCategoryById(id).getSubCategories();
	}
	
	@GetMapping("/api/categories/subcategories/{id}")
	public SubCategory getSubCategoryById(@PathVariable("id") Long id) {
		return catService.getSubCategoryById(id);
	}
	
	@PostMapping("/api/categories")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Category addCategory(@RequestBody Category category) {
		System.out.println(category.toString());
		return catService.addCategory(category);
	}
	
	@PostMapping("/api/categories/{id}/subcategories")
	public SubCategory addSubCategoryOfCategory(@RequestBody SubCategory subCat, @PathVariable("id") Long id) {
		Category cat = catService.getCategoryById(id);
		return catService.addSubCategory(cat, subCat);
	}
	
	@PutMapping("/api/categories/{id}")
	public Category updateCategory(@RequestBody Category updatedCategory, @PathVariable("id") Long id) {
		return catService.updateCategory(id, updatedCategory);
	}
	
	@PutMapping("/api/categories/{catId}/subcategories/{id}")
	public SubCategory updateSubCategory(@RequestBody SubCategory subCat, @PathVariable("catId") Long catId, @PathVariable("id") Long id) {
		return catService.updateSubCategory(catId, subCat);
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
