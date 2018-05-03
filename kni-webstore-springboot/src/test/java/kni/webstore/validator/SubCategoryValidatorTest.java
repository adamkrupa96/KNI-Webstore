package kni.webstore.validator;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import kni.webstore.model.Category;
import kni.webstore.model.SubCategory;
import kni.webstore.validators.SubCategoryValidator;

public class SubCategoryValidatorTest {

	private SubCategoryValidator subCatValid;
	private SubCategory subCat;
	private BindException errorLog;
	
	@Before
	public void setUp() throws Exception {
		subCatValid = new SubCategoryValidator();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSupports() {
		assertTrue(subCatValid.supports(SubCategory.class));
		assertFalse(subCatValid.supports(Random.class));
	}

	@Test
	public void testValidate() {
		subCat = new SubCategory(null);
		subCat.setCategory(new Category("Kategoria"));
		errorLog = getCleanLogFor(subCat);
		subCatValid.validate(subCat, errorLog);
		assertTrue(hasError(errorLog.getAllErrors(), "null_error"));
		
		
		Random rand = new Random();
		errorLog = new BindException(rand, "rand");
		subCatValid.validate(rand, errorLog);
		assertTrue(hasError(errorLog.getAllErrors(), "type_error"));
		
		subCat = new SubCategory("a");
		subCat.setCategory(new Category("Kategoria"));
		errorLog = getCleanLogFor(subCat);
		subCatValid.validate(subCat, errorLog);
		assertTrue(hasError(errorLog.getAllErrors(), "size_error"));
		
		String name = "";
		for (int i = 0; i < 40; i++) {
			name += "a";
		}
		subCat = new SubCategory(name);
		subCat.setCategory(new Category("Kategoria"));
		errorLog = getCleanLogFor(subCat);
		subCatValid.validate(subCat, errorLog);
		assertTrue(hasError(errorLog.getAllErrors(), "size_error"));
		
		subCat = new SubCategory("podkategoria1");
		subCat.setCategory(new Category("Kategoria"));
		errorLog = getCleanLogFor(subCat);
		subCatValid.validate(subCat, errorLog);
		assertTrue(hasError(errorLog.getAllErrors(), "exists_error"));
		
		subCat = new SubCategory("xxxx");
		subCat.setCategory(new Category("Kategoria"));
		subCat.setProducts(null);
		errorLog = getCleanLogFor(subCat);
		subCatValid.validate(subCat, errorLog);
		assertTrue(hasError(errorLog.getAllErrors(), "null_error"));
		

		
	}

	private BindException getCleanLogFor(SubCategory subCat) {
		return new BindException(subCat, "subCat");
	}
	
	private boolean hasError(List<ObjectError> errors, String errorCode) {
		for (ObjectError error : errors) {
			if (error.getCode().equals(errorCode)) {
				return true;
			}
		}
		return false;
	}
}
