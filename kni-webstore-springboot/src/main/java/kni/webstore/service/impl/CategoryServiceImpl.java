package kni.webstore.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kni.webstore.model.Category;
import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;
import kni.webstore.repository.CategoryRepository;
import kni.webstore.repository.ProductRepository;
import kni.webstore.repository.SubCategoryRepository;
import kni.webstore.service.CategoryService;
import kni.webstore.service.ProductService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final Logger log = Logger.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private SubCategoryRepository subCatRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private ProductService prodService;
	
	@Override
	public Category addCategory(Category category) {
		log.info("Category saved");
		return catRepo.save(category);
	}

	@Override
	public Category updateCategory(Long id, Category category) {
		if (catRepo.exists(id)) {
			
			for(SubCategory subCat : category.getSubCategories()) {
				subCat.setCategory(category);
			}
			
			log.info("Category updated");
			return catRepo.save(category);
			
		} else return category;
	}

	@Override
	public void deleteCategoryById(Long id) {
		Category cat = catRepo.findOne(id);
		Set<Product> prodsOfCategory = prodService.getProductsOfCategory(cat);
		
		for (Product p : prodsOfCategory) {
			p.setSubCategory(null);
			prodRepo.save(p);
		}
		
		catRepo.delete(id);
		log.info("Category deleted");
	}

	@Override
	public Category getCategoryById(Long id) {
		return catRepo.findOne(id);
	}

	@Override
	public Category getCategoryByName(String name) {
		return catRepo.getByName(name);
	}

	@Override
	public Set<Category> getAllCategories() {
		HashSet<Category> set = new HashSet<Category>(catRepo.findAll());
		return set;
	}
	
	//Podkategoria
	
	@Override
	public SubCategory addSubCategory(Category parent, SubCategory subCategory) {
		parent.getSubCategories().add(subCategory);
		subCategory.setCategory(parent);
		
		log.info("SubCategory saved");
		return subCatRepo.save(subCategory);
		
	}
	
	@Override
	public SubCategory updateSubCategory(Long categoryId, SubCategory subCategory) {
		subCategory.setCategory(catRepo.findOne(categoryId));
		
		System.out.println(subCategory.getName());
		System.out.println(subCategory.getCategory().getId());
		System.out.println(subCategory.getId());
		
		if(subCatRepo.exists(categoryId)) {
			log.info("Sub-Category updated");
			return subCatRepo.save(subCategory);
		} else return subCategory;
	
	}
	
	@Override
	public void deleteSubCategoryById(Long id) {
		SubCategory subCat = subCatRepo.findOne(id);
		
		for (Product p : subCat.getProducts()) {
			p.setSubCategory(null);
			prodRepo.save(p);
		}
		
		subCatRepo.delete(id);
		log.info("Sub-Category deleted");
	}
	
	@Override
	public void deleteSubCategoryByName(String name) {
		subCatRepo.deleteByName(name);
		log.info("Sub-Category deleted");
	}
	
	@Override
	public SubCategory getSubCategoryById(Long id) {
		return subCatRepo.findOne(id);
	}
	
	@Override
	public SubCategory getSubCategoryByName(String name) {
		return subCatRepo.getByName(name);
	}

	@Override
	public Set<SubCategory> getAllSubCategories() {
		HashSet<SubCategory> set = new HashSet<SubCategory>(subCatRepo.findAll());
		return set;
	}

}
