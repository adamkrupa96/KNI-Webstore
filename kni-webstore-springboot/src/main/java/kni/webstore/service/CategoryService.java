package kni.webstore.service;

import java.util.Set;

import kni.webstore.model.Category;
import kni.webstore.model.SubCategory;

public interface CategoryService {
	
	//Kategoria
	
	Category addCategory(Category category);
	void deleteCategoryById(Long id);
	boolean updateCategory(Long id, Category category);
	Category getCategoryById(Long id);
	Category getCategoryByName(String name);
	Set<Category> getAllCategories();
	
	//Podkategoria
	
	void addSubCategory(Category parent, SubCategory subCategory);
	boolean updateSubCategory(Long id, Category category, SubCategory subCategory);
	void deleteSubCategoryById(Long id);
	void deleteSubCategoryByName(String name);
	SubCategory getSubCategoryById(Long id);
	SubCategory getSubCategoryByName(String name);
	Set<SubCategory> getAllSubCategories();
}
