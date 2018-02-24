package kni.webstore.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kni.webstore.model.Category;
import kni.webstore.model.SubCategory;
import kni.webstore.service.CategoryService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class CategoryController {

	@Autowired
	private CategoryService catService;
	
	@GetMapping("/categories")
	public Set<Category> getAllCategories() {
		return catService.getAllCategories();
	}
	
	@GetMapping("/categories/{id}")
	public Category getCategoryById(@PathVariable("id") Long id) {
		return catService.getCategoryById(id);
	}
	
	@GetMapping("/categories/{id}/subcategories")
	public Set<SubCategory> getSubCategoriesOfCategory(@PathVariable("id") Long id) {
		return catService.getCategoryById(id).getSubCategories();
	}
	
	@GetMapping("/categories/subcategories/{id}")
	public SubCategory getSubCategoryById(@PathVariable("id") Long id) {
		return catService.getSubCategoryById(id);
	}
	
	@PostMapping("/categories")
	public Category addCategory(@RequestBody Category category) {
		return catService.addCategory(category);
	}
	
	@PostMapping("/categories/{id}/subcategories")
	public void addSubCategoryOfCategory(@RequestBody SubCategory subCat, @PathVariable("id") Long id) {
		Category cat = catService.getCategoryById(id);
		catService.addSubCategory(cat, subCat);
	}
	
	@PutMapping("/categories/{id}")
	public void updateCategory(@RequestBody Category updatedCategory, @PathVariable("id") Long id) {
		catService.updateCategory(id, updatedCategory);
	}
	
	@PutMapping("/categories/{catId}/subcategories/{id}")
	public void updateSubCategory(@RequestBody SubCategory subCat, @PathVariable("catId") Long catId, @PathVariable("id") Long id) {
		catService.updateSubCategory(catService.getCategoryById(catId), subCat);
	}
	
	@DeleteMapping("/categories/{id}")
	public void deleteCategory(@PathVariable("id") Long id) {
		catService.deleteCategoryById(id);
	}
	
	@DeleteMapping("/categories/subcategories/{id}")
	public void deleteSubCategory(@PathVariable("id") Long id) {
		catService.deleteSubCategoryById(id);
	}
}
