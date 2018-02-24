package kni.webstore.service;

import java.util.Set;

import kni.webstore.model.Category;
import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;

public interface ProductService {

	Product addProduct(SubCategory parent, Product product);
	Product updateProduct(SubCategory parent, Product product);
	void deleteProductById(Long id);
	void deleteAll();
	Product getProductById(Long id);
	Set<Product> getProductsOfCategory(Category cat);
	Set<Product> getAllProducts();
	Set<Product> getProductsWithoutCategory();

}
