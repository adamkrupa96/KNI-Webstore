package kni.webstore.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kni.webstore.model.Category;
import kni.webstore.model.SubCategory;
import kni.webstore.repository.CategoryRepository;
import kni.webstore.repository.SubCategoryRepository;
import kni.webstore.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {

	private final Logger log = Logger.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private SubCategoryRepository subCatRepo;
	
	@Override
	public Category addCategory(Category category) {
		log.info("Category saved");
		return catRepo.save(category);
	}

	@Override
	public boolean updateCategory(Long id, Category category) {

		if (catRepo.exists(id)) {
			catRepo.save(category);
			log.info("Category updated");
			return true;
		} else
			return false;

	}

	@Override
	public void deleteCategoryById(Long id) {
		catRepo.delete(id);
		log.info("Category deleted");
	}

	@Override
	public void deleteCategoryByName(String name) {
		catRepo.deleteByName(name);
		log.info("Category deleted");
	}

	@Override
	public Category getCategoryById(Long id) {
		return catRepo.getOne(id);
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
	public void addSubCategory(Category parent, SubCategory subCategory) {
		parent.getSubCategories().add(subCategory);
		subCategory.setCategory(parent);
		
		catRepo.save(parent);
		log.info("SubCategory saved");
	}
	
	@Override
	public boolean updateSubCategory(Long id, Category category, SubCategory subCategory) {
		subCategory.setCategory(category); //Uzupełnienie dowiązania obustronnego
		if(subCatRepo.exists(id)) {
			subCatRepo.save(subCategory);
			log.info("Sub-Category updated");
			return true;
		} else return false;
	
	}
	
	@Override
	public void deleteSubCategoryById(Long id) {
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
		return subCatRepo.getOne(id);
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
