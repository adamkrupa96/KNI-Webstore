package kni.webstore;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kni.webstore.model.Category;
import kni.webstore.model.Feature;
import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;
import kni.webstore.service.CategoryService;

@SpringBootApplication
public class Webstore {
	
	@Autowired
	private CategoryService catService;
	
	public static void main(String[] args) {
		SpringApplication.run(Webstore.class, args);
	}
	
	@PostConstruct //Metoda wywołana odrazu po stworzeniu kontextu springa
	public void test() {
		Category undefinied = new Category("Laptop");
		SubCategory subUndefinied = new SubCategory("15CAL", new HashSet<Product>(), undefinied);
		undefinied.getSubCategories().add(subUndefinied);
		Product p = new Product("Lenovo", "Y700", 3000.2, 10, subUndefinied, new HashSet<Feature>());
		subUndefinied.getProducts().add(p);
		
		catService.addCategory(undefinied);
	}
}
