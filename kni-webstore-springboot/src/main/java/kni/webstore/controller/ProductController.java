package kni.webstore.controller;

import java.util.Set;

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
	public Set<Product> getAllProducts() {
		return prodService.getAllProducts();
	}
	
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable("id") Long id) {
		return prodService.getProductById(id);
	}
	
	@GetMapping("/productsOfCategory")
	public Set<Product> getProductsOfCategory(@RequestParam("cat") Long id) {
		return prodService.getProductsOfCategory(catService.getCategoryById(id));
	}
	
	@GetMapping("/productsOfSubCategory")
	public Set<Product> getProductsOfSubCategory(@RequestParam("sub") Long id) {
		return catService.getSubCategoryById(id).getProducts();
	}
	
	@GetMapping("/productsUnallocated")
	public Set<Product> getUnallocatedProducts() {
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
	
//	@PutMapping("/products/{id}/?sub={sub_id}")
//	public Product updateProductWithSubCategory(@RequestBody Product product, @PathVariable("subId") Long subId, @PathVariable("id") Long id) {
//		return prodService.updateProductWithSubCategory(id, product, catService.getSubCategoryById(subId));
//	}
	
//	@PutMapping("/products/{id}")
//	public Product updateProductx(@RequestBody Product product, @PathVariable("id") Long id) {
//		return prodService.updateProduct(id, product);
//	}
	
	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable("id") Long id) {
		prodService.deleteProductById(id);
	}
	

	@DeleteMapping("/products")
	public void delteAll() {
		prodService.deleteAll();
	}
}
