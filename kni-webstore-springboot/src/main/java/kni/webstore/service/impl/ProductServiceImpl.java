package kni.webstore.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;
import kni.webstore.repository.ProductRepository;
import kni.webstore.repository.SubCategoryRepository;
import kni.webstore.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final Logger log = Logger.getLogger(ProductService.class);

	@Autowired
	private SubCategoryRepository subCatRepo;

	@Autowired
	private ProductRepository prodRepo;

	@Override
	public void addProduct(SubCategory parent, Product product) {
		parent.getProducts().add(product);
		product.setSubCategory(parent);

		subCatRepo.save(parent);
		log.info("Product saved");
	}

	@Override
	public boolean updateProduct(Long id, SubCategory subCat, Product product) {
		product.setSubCategory(subCat);
		
		if (prodRepo.exists(id)) {
			prodRepo.save(product);
			log.info("Product updated");
			return true;
		} else
			return false;

	}

	@Override
	public void deleteProductById(Long id) {
		prodRepo.delete(id);
		log.info("Product deleted");
	}

	@Override
	public Product getProductById(Long id) {
		return prodRepo.getOne(id);
	}

	@Override
	public Set<Product> getAllProducts() {
		HashSet<Product> set = new HashSet<Product>(prodRepo.findAll());
		return set;
	}

}
