package kni.webstore.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kni.webstore.model.Category;
import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;
import kni.webstore.repository.ProductRepository;
import kni.webstore.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final Logger log = Logger.getLogger(ProductService.class);

	@Autowired
	private ProductRepository prodRepo;

	@Override
	public Product addProduct(SubCategory parent, Product product) {
		parent.getProducts().add(product);
		product.setSubCategory(parent);

		log.info("Product saved");
		return prodRepo.save(product);

	}

	@Override
	public Product updateProduct(SubCategory subCategory, Product product) {
		product.setSubCategory(subCategory);
		
		if(prodRepo.exists(product.getId())) {
			log.info("Sub-Category updated");
			return prodRepo.save(product);
		} else return product;
	
	}
	
	
	@Override
	public void deleteProductById(Long id) {
		prodRepo.delete(id);
		log.info("Product deleted");
	}
	
	@Override
	public Set<Product> getProductsOfCategory(Category cat) {
		Set<Product> prodsOfCat = new HashSet<Product>();
		
		for (SubCategory subCat : cat.getSubCategories()) {
			prodsOfCat.addAll(subCat.getProducts());
		}
		
		return prodsOfCat;
	}
	
	@Override
	public Set<Product> getProductsWithoutCategory() {
		Set<Product> products = new HashSet<Product>();
		
		for (Product product : this.getAllProducts()) {
			if(product.getSubCategory() == null) 
				products.add(product);
		}
		
		return products;
	}
	
	@Override
	public Product getProductById(Long id) {
		return prodRepo.findOne(id);
	}

	@Override
	public Set<Product> getAllProducts() {
		HashSet<Product> set = new HashSet<Product>(prodRepo.findAll());
		return set;
	}

	@Override
	public void deleteAll() {
		prodRepo.deleteAll();
	}
}
