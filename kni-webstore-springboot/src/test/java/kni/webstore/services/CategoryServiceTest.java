package kni.webstore.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kni.webstore.model.Category;
import kni.webstore.model.SubCategory;
import kni.webstore.service.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

	@Autowired
	private CategoryService catService;
	private Category headPhones;

	@Before
	public void setUp() throws Exception {
		headPhones = new Category("Headphones");
		headPhones = catService.addCategory(headPhones);
	}

	@After
	public void tearDown() throws Exception {
		if (catService.categoryExist("Headphones"))
			catService.deleteCategoryById(headPhones.getId());
	}

	@Test
	public void shouldAddCategory() {

		String expected = "Headphones";
		String actual = catService.getCategoryById(headPhones.getId()).getName();

		assertEquals(expected, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAddSubCategoryWithoutCategory() {
		SubCategory orphanSubCat = new SubCategory("Orphan");
		orphanSubCat = catService.addSubCategory(null, orphanSubCat);
	}

	@Test
	public void testAddSubCategory() {
		SubCategory closed = new SubCategory("Closed");
		closed = catService.addSubCategory(headPhones, closed);
		
		SubCategory fetched = catService.getSubCategoryById(closed.getId());
		assertNotNull(fetched);
		assertNotNull(fetched.getCategory());
		
		headPhones = catService.getCategoryById(headPhones.getId());
		
		boolean contains = false;
		for (SubCategory subCat : headPhones.getSubCategories()) {
			if (subCat.getId() == fetched.getId()) {
				contains = true;
			}
		}
		assertTrue(contains);
	}
	
	
	@Test
	public void shouldAddEmptySubCatsToCategory() {
		SubCategory open = new SubCategory("Open");
		SubCategory close = new SubCategory("Close");

		open = catService.addSubCategory(headPhones, open);
		close = catService.addSubCategory(headPhones, close);

		headPhones = catService.getCategoryById(headPhones.getId());
		List<SubCategory> headPhonesSubs = headPhones.getSubCategories();

		assertNotNull(headPhonesSubs);
		assertTrue(headPhonesSubs.size() == 2);

		headPhonesSubs.forEach(sub -> assertTrue(sub.getName().equals("Open") || sub.getName().equals("Close")));

		catService.deleteSubCategoryById(open.getId());
		catService.deleteSubCategoryById(close.getId());

		headPhones = catService.getCategoryById(headPhones.getId());

		assertTrue(headPhones.getSubCategories().size() == 0);
	}

	@Test
	public void shouldUpdateCategory() {
		Long idBeforeUpdate = headPhones.getId();
		headPhones.setName("Mouses");
		catService.updateCategory(headPhones.getId(), headPhones);

		String expected = "Mouses";
		Category actualObj = catService.getCategoryById(headPhones.getId());
		String actual = actualObj.getName();

		assertEquals(expected, actual);
		assertEquals(idBeforeUpdate, actualObj.getId());
	}
	
	@Test
	public void testCategoryExistsByName() {
		boolean headphonesExist = catService.categoryExist("Headphones");
		boolean customExist = catService.categoryExist("nie ma takiej kategorii chlopie");
		
		assertTrue(headphonesExist);
		assertFalse(customExist);
	}
	
	@Test
	public void shouldDeleteCategoryWithoutChilds() {
		Long idToDelete = headPhones.getId();
		catService.deleteCategoryById(idToDelete);

		assertNull(catService.getCategoryById(idToDelete));
	}

	@Test
	public void shouldDeleteSingleSubCategory() {
		SubCategory open = new SubCategory("Open");
		SubCategory close = new SubCategory("Close");

		open = catService.addSubCategory(headPhones, open);
		close = catService.addSubCategory(headPhones, close);

		catService.deleteSubCategoryById(open.getId());
		headPhones = catService.getCategoryById(headPhones.getId());

		assertTrue(headPhones.getSubCategories().size() == 1);
		assertEquals("Close", headPhones.getSubCategories().get(0).getName());
		assertEquals("Close", catService.getSubCategoryById(close.getId()).getName());

		catService.deleteSubCategoryById(close.getId());
	}

	@Test
	public void shouldDeleteCategoryWithEmptySubcats() {
		SubCategory open = new SubCategory("Open");
		SubCategory close = new SubCategory("Close");

		open = catService.addSubCategory(headPhones, open);
		close = catService.addSubCategory(headPhones, close);

		headPhones = catService.getCategoryById(headPhones.getId());
		List<SubCategory> headPhonesSubs = headPhones.getSubCategories();

		assertNotNull(headPhonesSubs);
		assertTrue(headPhonesSubs.size() == 2);

		headPhonesSubs.forEach(sub -> assertTrue(sub.getName().equals("Open") || sub.getName().equals("Close")));

		catService.deleteCategoryById(headPhones.getId());

		assertNull(catService.getSubCategoryById(open.getId()));
		assertNull(catService.getSubCategoryById(close.getId()));
	}

	@Test
	public void shouldGetCategoryById() {

		Long idToGet = headPhones.getId();

		String expected = "Headphones";
		String actual = catService.getCategoryById(idToGet).getName();

		assertEquals(expected, actual);
	}

	@Test
	public void shouldGetCategoryByName() {

		String expected = "Headphones";
		String actual = catService.getCategoryByName("Headphones").getName();

		assertEquals(expected, actual);
	}

	@Test
	public void shouldGetAllCategories() {
		List<Category> allCategories = catService.getAllCategories();

		assertNotNull(allCategories);
		assertTrue(allCategories.size() > 0);
	}

	@Test
	public void shouldCheckCategoryExist() {
		assertTrue(catService.categoryExist(headPhones.getName()));
		assertFalse(catService.categoryExist("NIE ISTNIEJACA"));
	}

}
