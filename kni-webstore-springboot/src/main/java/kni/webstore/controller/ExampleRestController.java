package kni.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kni.webstore.model.Category;
import kni.webstore.model.Feature;
import kni.webstore.repository.CategoryRepository;
import kni.webstore.repository.FeatureRepository;
import kni.webstore.repository.ProductRepository;
import kni.webstore.repository.SubCategoryRepository;

@RestController
@RequestMapping("/api")
public class ExampleRestController {

	@Autowired
	private CategoryRepository catRepo;
	@Autowired
	private ProductRepository prodRepo;
	@Autowired
	private SubCategoryRepository subCatRepo;
	@Autowired
	private FeatureRepository featRepo;
	
	//Testy
	@GetMapping("/example")
	public Category example() {
		Feature f = featRepo.findByName("Kolor");
		f.setName("Color");
		featRepo.save(f);
		Category category = catRepo.findOne(new Long(1));
		
		return category;
	}
}