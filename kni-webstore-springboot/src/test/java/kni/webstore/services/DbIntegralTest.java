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
public class DbIntegralTest {

	@Autowired
	private CategoryService catService;
	@Autowired
	private ProductService prodService;
	
	private Category withoutChilds;
	
	private Category withEmptyChilds;
	private SubCategory emptyChildOne;
	private SubCategory emptyChildTwo;
	
	
	private Category headPhones;
	private SubCategory open;
	private SubCategory close;
	private Product tdkLX;
	
	private Product orphanProduct;
	
	
	@Before
	public void setUp() throws Exception {
		withoutChilds = new Category("Without childs");
		withoutChilds = catService.addCategory(withoutChilds);
		
		withEmptyChilds = new Category("With empty childs");
		withEmptyChilds = catService.addCategory(withEmptyChilds);
		emptyChildOne = new SubCategory("Empty child #1");
		emptyChildTwo = new SubCategory("Empty child #2");
		emptyChildOne = catService.addSubCategory(withEmptyChilds, emptyChildOne);
		emptyChildTwo = catService.addSubCategory(withEmptyChilds, emptyChildTwo);
		
		
		headPhones = new Category("Headphones");
		open = new SubCategory("Open");
		close = new SubCategory("Close");
		tdkLX = new Product("TDK", "LX", 123.23, 10);
		tdkLX.addFeature("Color", "RED");
		
		headPhones = catService.addCategory(headPhones);
		open = catService.addSubCategory(headPhones, open);
		close = catService.addSubCategory(headPhones, close);
		tdkLX = prodService.addProductToSubCategory(close, tdkLX);
		close = catService.getSubCategoryById(close.getId());
		
		orphanProduct = new Product("Orph", "Orph", 21.2, 10);
		prodService.addProductWithoutSubCategory(orphanProduct);
	}

	@After
	public void tearDown() throws Exception {

	}
	
	@Test
	public void deleteCategoryWithoutChilds() {
		catService.deleteCategoryById(withoutChilds.getId());
		
		assertNull(catService.getCategoryById(withoutChilds.getId()));
	}
	
	@Test
	public void deleteCategoryWithEmptyChilds() {
		catService.deleteCategoryById(withEmptyChilds.getId());
		
		Category fetchedById = catService.getCategoryById(withEmptyChilds.getId());
		SubCategory subCatOne = catService.getSubCategoryById(emptyChildOne.getId());
		SubCategory subCatTwo = catService.getSubCategoryById(emptyChildTwo.getId());
		
		assertNull(fetchedById);
		assertNull(subCatOne);
		assertNull(subCatTwo);
	}
	
	@Test
	public void deleteCategoryWithChildsThatHasChilds() {
		catService.deleteCategoryById(headPhones.getId());
		
		SubCategory openAfterDelete = catService.getSubCategoryById(open.getId());
		assertNull(openAfterDelete);
		
		SubCategory closeAfterDelete = catService.getSubCategoryById(close.getId());
		assertNull(closeAfterDelete);
		
		Product productOfCloseAfterDelete = prodService.getProductById(tdkLX.getId());
		assertNotNull(productOfCloseAfterDelete);
		assertNull(productOfCloseAfterDelete.getSubCategory());
		assertEquals("TDK", productOfCloseAfterDelete.getBrand());
	}
	
	@Test
	public void deleteEmptySubCategory() {
		catService.deleteSubCategoryById(emptyChildOne.getId());
		
		emptyChildOne = catService.getSubCategoryById(emptyChildOne.getId());
		assertNull(emptyChildOne);
		
		emptyChildTwo = catService.getSubCategoryById(emptyChildTwo.getId());
		assertNotNull(emptyChildTwo);
		
		withEmptyChilds = catService.getCategoryById(withEmptyChilds.getId());
		assertNotNull(withEmptyChilds);
	}
	
	@Test
	public void delteSubCategoryWithChilds() {
		catService.deleteSubCategoryById(close.getId());
		
		assertNull(catService.getSubCategoryById(close.getId()));
		
		tdkLX = prodService.getProductById(tdkLX.getId());
		assertNotNull(tdkLX);
		assertEquals("TDK", tdkLX.getBrand());
		assertNull(tdkLX.getSubCategory());
		
		assertNotNull(catService.getCategoryById(headPhones.getId()));
		assertNotNull(catService.getSubCategoryById(open.getId()));
	}
	
	@Test
	public void deleteOrphanProduct() {
		prodService.deleteProductById(orphanProduct.getId());
		assertNull(prodService.getProductById(orphanProduct.getId()));
	}
	
	@Test
	public void deleteNotOrphanProduct() {
		prodService.deleteProductById(tdkLX.getId());
		assertNull(prodService.getProductById(tdkLX.getId()));
		
		assertNotNull(catService.getSubCategoryById(close.getId()));
		assertNotNull(catService.getCategoryById(headPhones.getId()));
	}
}
