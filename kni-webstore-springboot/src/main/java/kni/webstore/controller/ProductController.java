package kni.webstore.controller;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kni.webstore.model.Product;
import kni.webstore.service.CategoryService;
import kni.webstore.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
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
	
	@PostMapping("/subcategories/{id}/products")
	public void addProduct(@RequestBody Product product, @PathVariable("id") Long id) {
		prodService.addProduct(catService.getSubCategoryById(id), product);
	}
	
	@PutMapping("/subcategories/{subId}/products/{id}")
	public void updateProduct(@RequestBody Product product, @PathVariable("subId") Long subId, @PathVariable("id") Long id) {
		//Uzupełnienie dwustronnego powiązania ignorowanego przez @JSONIgnore
		prodService.updateProduct(id, catService.getSubCategoryById(subId), product);
	}
	
	//DeleteMapping
	
}
