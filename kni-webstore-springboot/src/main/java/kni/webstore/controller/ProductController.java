package kni.webstore.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kni.webstore.model.Category;
import kni.webstore.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class ProductController {
	
	@Autowired
	private ProductService prodService;
	
	@GetMapping("/categories")
	public Set<Category> getAllCategories() {
		return prodService.getAllCategories();
	}
}
