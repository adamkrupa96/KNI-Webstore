package kni.webstore.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kni.webstore.model.Product;
import kni.webstore.service.CategoryService;
import kni.webstore.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService prodService;
	
	@Autowired
	private CategoryService catService;
	
	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return prodService.getAllProducts();
	}
	
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable("id") Long id) {
		return prodService.getProductById(id);
	}
	
	@GetMapping("/productsOfCategory")
	public List<Product> getProductsOfCategory(@RequestParam("cat") Long id) {
		return prodService.getProductsOfCategory(catService.getCategoryById(id));
	}
	
	@GetMapping("/productsOfSubCategory")
	public List<Product> getProductsOfSubCategory(@RequestParam("sub") Long id) {
		return catService.getSubCategoryById(id).getProducts();
	}
	
	@GetMapping("/productsUnallocated")
	public List<Product> getUnallocatedProducts() {
		return prodService.getProductsWithoutCategory();
	}

	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product, 
			@RequestParam("sub") String subCategory) {
		if(subCategory.equals("null")) {
			return prodService.addProduct(product);
		} else {
			return prodService.addProductToSubCategory(catService.getSubCategoryById(Long.parseLong(subCategory)), product);
		}

	}
	
	@PutMapping("/products/{id}")
	public Product updateProduct(@PathVariable("id") Long id, 
			@RequestBody Product product, 
			@RequestParam("sub") String subCategory) {

		if(subCategory.equals("null")) {
			product.setId(id);
			return prodService.updateProduct(id, product);
		} else {
			product.setId(id);
			return prodService.updateProductWithSubCategory(id, product, 
					catService.getSubCategoryById(Long.parseLong(subCategory)));
		}
	}
	
	
	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable("id") Long id) {
		prodService.deleteProductById(id);
	}
	

	@DeleteMapping("/products")
	public void delteAll() {
		prodService.deleteAll();
	}
}
