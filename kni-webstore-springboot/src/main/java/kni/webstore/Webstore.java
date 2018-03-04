package kni.webstore;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kni.webstore.model.Category;
import kni.webstore.model.Feature;
import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;
import kni.webstore.service.CategoryService;
import kni.webstore.service.ProductService;

@SpringBootApplication
public class Webstore {
	
	@Autowired
	private CategoryService catService;
	
	@Autowired
	private ProductService prodService;
	
	public static void main(String[] args) {
		SpringApplication.run(Webstore.class, args);
	}
	
	@PostConstruct //Metoda wywołana odrazu po stworzeniu kontextu springa
	public void test() {
		Category undefinied = new Category("Laptop");
		SubCategory subUndefinied = new SubCategory("15CAL", new ArrayList<Product>());
		Product p = new Product("Lenovo", "Y700", 3000.2, 10, new ArrayList<Feature>());
		
		Category cat = catService.addCategory(undefinied); 
		SubCategory subCat = catService.addSubCategory(cat, new SubCategory("ELO", new ArrayList<Product>()));
		Product prod = prodService.addProductToSubCategory(subCat, p);
		
		prodService.deleteProductById(new Long(1));
		
		for (Product px : prodService.getProductsWithoutCategory()) {
			System.out.println(px.toString());
		}
		
	}
}
