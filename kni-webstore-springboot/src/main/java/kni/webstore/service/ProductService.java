package kni.webstore.service;

import java.util.Set;

import kni.webstore.model.Category;
import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;

public interface ProductService {

	void addCategory(Category category);
	void addSubCategory(Category parent, SubCategory subCategory);
	void addProduct(Category category, SubCategory parent, Product product);
	
	//Jeśli istnieje, nadpisuje. Jeśli nie istnieje, nie robi nic
	boolean updateCategory(Long id, Category category); 
	boolean updateSubCategory(Long id, SubCategory subCategory);
	boolean updateProduct(Long id, Product product);
	
	void deleteCategoryById(Long id);
	void deleteSubCategoryById(Long id);
	void deleteProductById(Long id);
	
	void deleteCategoryByName(String name);
	void deleteSubCategoryByName(String name);
	
	Category getCategoryById(Long id);
	SubCategory getSubCategoryById(Long id);
	Product getProductById(Long id);
	
	Category getCategoryByName(String name);
	SubCategory getSubCategoryByName(String name);
	
	Set<Category> getAllCategories();
	Set<SubCategory> getAllSubCategories();
	Set<Product> getAllProducts();
	
	//Inne metody związane z produktami, np. Obsługa zamówień
}
