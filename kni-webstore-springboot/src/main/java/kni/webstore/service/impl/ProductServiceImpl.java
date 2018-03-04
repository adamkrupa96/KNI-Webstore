package kni.webstore.service.impl;


import java.util.ArrayList;
import java.util.List;

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
	public Product addProductToSubCategory(SubCategory parent, Product product) {
		parent.getProducts().add(product);
		product.setSubCategory(parent);

		log.info("Product saved");
		return prodRepo.save(product);

	}
	
	@Override
	public Product addProduct(Product product) {
		product.setSubCategory(null);
		
		log.info("Product saved");
		return prodRepo.save(product);
	}

	@Override
	public Product updateProductWithSubCategory(Long id, Product product, SubCategory subCategory) {
		product.setSubCategory(subCategory);
		
		if(prodRepo.exists(id)) {
			log.info("Product updated");
			return prodRepo.save(product);
		} else return product;
	
	}
	
	@Override
	public Product updateProduct(Long id, Product product) {
		product.setSubCategory(null);
		
		if(prodRepo.exists(id)) {
			log.info("Product updated");
			return prodRepo.save(product);
		} else return product;
	}
	
	@Override
	public void deleteProductById(Long id) {
		prodRepo.delete(id);
		log.info("Product deleted");
	}
	
	@Override
	public List<Product> getProductsOfCategory(Category cat) {
		List<Product> prodsOfCat = new ArrayList<Product>();
		
		for (SubCategory subCat : cat.getSubCategories()) {
			prodsOfCat.addAll(subCat.getProducts());
		}
		
		return prodsOfCat;
	}
	
	@Override
	public List<Product> getProductsWithoutCategory() {
		List<Product> products = new ArrayList<Product>();
		
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
	public List<Product> getAllProducts() {
		return prodRepo.findAll();
	}

	@Override
	public void deleteAll() {
		prodRepo.deleteAll();
	}
}
