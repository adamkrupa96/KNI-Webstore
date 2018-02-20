package kni.webstore;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kni.webstore.model.Category;
import kni.webstore.service.ProductService;

@SpringBootApplication
public class Webstore {
	
	@Autowired
	private ProductService prodService;
	
	public static void main(String[] args) {
		SpringApplication.run(Webstore.class, args);
	}
	
	@PostConstruct //Metoda wywołana odrazu po stworzeniu kontextu springa
	public void test() {
		prodService.addCategory(new Category("Laptopy"));
		prodService.addCategory(new Category("PC"));
		prodService.addCategory(new Category("Myszki"));
	}
}
