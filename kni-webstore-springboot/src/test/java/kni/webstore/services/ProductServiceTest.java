package kni.webstore.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kni.webstore.model.Category;
import kni.webstore.model.Product;
import kni.webstore.model.SubCategory;
import kni.webstore.service.CategoryService;
import kni.webstore.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService prodService;
	
	@Autowired
	private CategoryService catService;
	
	private Category category;
	private SubCategory subCategory;
	private Product product;
	
	@Before
	public void setUp() throws Exception {
		category = new Category("Category");
		category = catService.addCategory(category);
		
		subCategory = new SubCategory("Subcategory");
		subCategory = catService.addSubCategory(category, subCategory);
		
		product = new Product("brand", "model", 12.2, 10);
		product.addFeature("color", "red");
		product.addFeature("chuj", "CHUJ");
	}

	@After
	public void after() throws Exception {
		prodService.deleteAll();
	}
	
	@Test
	public void shouldAddProductToSubCategory() {
		product = prodService.addProductToSubCategory(subCategory, product);
		subCategory = catService.getSubCategoryById(subCategory.getId());
		
		assertNotNull(product);
		assertTrue(product.getId() != 0);
		assertEquals("Subcategory", product.getSubCategory().getName());
		assertTrue(subCategory.getProducts().contains(product));
		assertTrue(product.getFeatures().size() == 2);
	}
	
	@Test
	public void shouldAddOrhpan() {
		product = prodService.addProductWithoutSubCategory(product);
		
		assertNotNull(product);
		assertTrue(product.getId() != 0);
		assertNull(product.getSubCategory());
	}
	
	@Test
	public void shouldUpdateProdWithSubCategory() {
		product = prodService.addProductToSubCategory(subCategory, product);
		product.setBrand("DILDO");
		product = prodService.updateProductWithSubCategory(product.getId(), product, subCategory);
		
		assertEquals("DILDO", product.getBrand());
	}
	
	@Test
	public void shouldGetProductsWithoutSubcategory() {
		Product orph1 = new Product("brand", "model", 12.2, 10);
		Product orph2 = new Product("bww", "mzz", 123.2, 100);
		
		orph1 = prodService.addProductWithoutSubCategory(orph1);
		orph2 = prodService.addProductWithoutSubCategory(orph2);
		
		assertTrue(prodService.getProductsWithoutCategory().size() == 2);
	}
}
