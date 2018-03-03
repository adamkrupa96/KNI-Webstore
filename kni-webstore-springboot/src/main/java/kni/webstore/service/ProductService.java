package kni.webstore.service;

import java.util.Set;

import kni.webstore.model.Category;
import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;

public interface ProductService {

	Product addProductToSubCategory(SubCategory parent, Product product);
	Product addProduct(Product product);
	Product updateProductWithSubCategory(Long id, Product product, SubCategory parent);
	Product updateProduct(Long id, Product product);
	void deleteProductById(Long id);
	void deleteAll();
	Product getProductById(Long id);
	Set<Product> getProductsOfCategory(Category cat);
	Set<Product> getAllProducts();
	Set<Product> getProductsWithoutCategory();

}
