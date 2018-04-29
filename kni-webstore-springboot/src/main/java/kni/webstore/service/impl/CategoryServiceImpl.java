package kni.webstore.service.impl;

import java.util.List;

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
			
			completeBiDirectionalConnection(category);
			log.info("Category updated");
			return catRepo.save(category);

		} else {
			log.info("Category not updated, because entity with id " + id + " not exist.");
			return null;
		}
	}
	
	private void completeBiDirectionalConnection(Category category) {
		category.getSubCategories().forEach(subCategory -> subCategory.setCategory(category));
	}
	
	@Override
	public void deleteCategoryById(Long id) {
		Category categoryToDelete = catRepo.findOne(id);
		
		List<Product> prodsOfCategory = prodService.getProductsOfCategory(categoryToDelete);
		makeProductsOrphanAndUpdate(prodsOfCategory);
		
		catRepo.delete(id);
		
		log.info("Category deleted");
	}
	
	private void makeProductsOrphanAndUpdate(List<Product> productsOfCategory) {
		productsOfCategory.forEach(product -> {
			product.setSubCategory(null);
			prodService.updateProductWithoutSubCategory(product.getId(), product);
		});
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
	public List<Category> getAllCategories() {
		return catRepo.findAll();
	}

	@Override
	public Boolean categoryExist(String name) {
		for (Category category : catRepo.findAll()) {
			String categoryName = category.getName().toUpperCase().trim();
			String lookingForName = name.toUpperCase().trim();
			
			if (categoryName.equals(lookingForName)) {
				return true;
			}
		}
		return false;
	}

	// Podkategoria

	@Override
	public SubCategory addSubCategory(Category parent, SubCategory subCategory) throws IllegalArgumentException {
		if (parent == null) {
			log.error("You can't create Subcategory of null");
			throw new IllegalArgumentException();
		}
		
		parent.addSubCategory(subCategory);
		
		log.info("SubCategory saved");
		return subCatRepo.save(subCategory);
	}

	@Override
	public SubCategory updateSubCategoryOfCategory(Long categoryId, SubCategory subCategory) {
		Category categoryOfSubCategory = catRepo.findOne(categoryId);
		subCategory.setCategory(categoryOfSubCategory);

		if (subCatRepo.exists(subCategory.getId())) {
			log.info("Sub-Category updated");
			return subCatRepo.save(subCategory);
		} else {
			log.info("Sub-Category not updated. Entity with this id doesn't exist.");
			return null;
		}
	}

	@Override
	public void deleteSubCategoryById(Long id) {
		SubCategory subCat = subCatRepo.findOne(id);
		
		subCat.getProducts().forEach(product -> {
			product.setSubCategory(null);
			prodRepo.save(product);
		});

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
	public List<SubCategory> getAllSubCategories() {
		return subCatRepo.findAll();
	}
}
