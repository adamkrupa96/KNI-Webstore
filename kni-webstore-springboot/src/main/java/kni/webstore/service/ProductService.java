package kni.webstore.service;

import java.util.Set;

import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;

public interface ProductService {

	void addProduct(SubCategory parent, Product product);
	boolean updateProduct(Long id,SubCategory subCat ,Product product);
	void deleteProductById(Long id);
	Product getProductById(Long id);
	Set<Product> getAllProducts();

}
