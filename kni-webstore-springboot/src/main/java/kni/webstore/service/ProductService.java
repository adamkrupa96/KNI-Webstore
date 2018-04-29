package kni.webstore.service;


import java.util.List;

import kni.webstore.model.Category;
import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;

public interface ProductService {

	Product addProductToSubCategory(SubCategory parent, Product product);
	Product addProductWithoutSubCategory(Product product);
	Product updateProductWithSubCategory(Long id, Product product, SubCategory parent);
	Product updateProductWithoutSubCategory(Long id, Product product); // TODO zmiana nazwy na UpdateProductWithoutSubCategory
	void deleteProductById(Long id);
	void deleteAll();
	Product getProductById(Long id);
	List<Product> getProductsOfCategory(Category cat);
	List<Product> getAllProducts();
	List<Product> getProductsWithoutCategory();

}
