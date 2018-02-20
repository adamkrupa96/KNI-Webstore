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
import kni.webstore.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private Logger log = Logger.getLogger(ProductService.class);
	
	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private SubCategoryRepository subCatRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Override
	public void addCategory(Category category) {
		catRepo.save(category);
		log.info("Category saved");
	}

	@Override
	public void addSubCategory(Category parent, SubCategory subCategory) {
		parent.getSubCategories().add(subCategory);
		
		if( !subCategory.getCategory().equals(parent) ) subCategory.setCategory(parent);
		
		catRepo.save(parent);
		log.info("SubCategory saved");
	}

	@Override
	public void addProduct(SubCategory parent, Product product) {
		parent.getProducts().add(product);
		
		if( !product.getSubCategory().equals(parent) ) product.setSubCategory(parent);
		
		subCatRepo.save(parent);
		log.info("Product saved");
	}

	@Override
	public boolean updateCategory(Category category) {
		
		if(catRepo.exists(category.getId())) {
			catRepo.save(category);
			log.info("Category updated");
			return true;
		} else return false;
				
	}

	@Override
	public boolean updateSubCategory(SubCategory subCategory) {
		
		if(subCatRepo.exists(subCategory.getId())) {
			subCatRepo.save(subCategory);
			log.info("Sub-Category updated");
			return true;
		} else return false;
	
	}

	@Override
	public boolean updateProduct(Product product) {
		
		if(prodRepo.exists(product.getId())) {
			prodRepo.save(product);
			log.info("Product updated");
			return true;
		} else return false;
		
	}

	@Override
	public void deleteCategoryById(Long id) {
		catRepo.delete(id);
		log.info("Category deleted");
	}

	@Override
	public void deleteSubCategoryById(Long id) {
		subCatRepo.delete(id);
		log.info("Sub-Category deleted");
	}

	@Override
	public void deleteProductById(Long id) {
		prodRepo.delete(id);
		log.info("Product deleted");
	}

	@Override
	public void deleteCategoryByName(String name) {
		catRepo.deleteByName(name);
		log.info("Category deleted");
	}

	@Override
	public void deleteSubCategoryByName(String name) {
		subCatRepo.deleteByName(name);
		log.info("Sub-Category deleted");
	}

	@Override
	public Category getCategoryById(Long id) {
		return catRepo.getOne(id);
	}

	@Override
	public SubCategory getSubCategoryById(Long id) {
		return subCatRepo.getOne(id);
	}

	@Override
	public Product getProductById(Long id) {
		return prodRepo.getOne(id);
	}

	@Override
	public Category getCategoryByName(String name) {
		return catRepo.getByName(name);
	}

	@Override
	public SubCategory getSubCategoryByName(String name) {
		return subCatRepo.getByName(name);
	}

	@Override
	public Set<Category> getAllCategories() {
		HashSet<Category> set = new HashSet<Category>(catRepo.findAll());
		return set;
	}

	@Override
	public Set<SubCategory> getAllSubCategories() {
		HashSet<SubCategory> set = new HashSet<SubCategory>(subCatRepo.findAll());
		return set;
	}

	@Override
	public Set<Product> getAllProducts() {
		HashSet<Product> set = new HashSet<Product>(prodRepo.findAll());
		return set;
	}

}
