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
import kni.webstore.repository.CategoryRepository;

@SpringBootApplication
public class Webstore {
	
	@Autowired
	private CategoryRepository catRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Webstore.class, args);
	}
	
	@PostConstruct
	public void testRepo() {
		Category cat = new Category("Laptopy", new HashSet<SubCategory>());
		SubCategory sCat = new SubCategory("15cali", new HashSet<Product>(), cat);
		cat.getSubCategories().add(sCat);
		
		Product laptop = new Product("LENOVO", "Y700", 3000.20, 10,sCat, new HashSet<Feature>());
		laptop.addFeature("Kolor", "Czarny");
		laptop.addFeature("Typ Ekranu", "LED");
		sCat.getProducts().add(laptop);
		
		catRepo.save(cat);
	}
}
