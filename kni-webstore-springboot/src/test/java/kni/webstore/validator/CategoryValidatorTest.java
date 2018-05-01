package kni.webstore.validator;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import kni.webstore.model.Category;
import kni.webstore.validators.CategoryValidator;

public class CategoryValidatorTest {

	private Category category;
	private CategoryValidator catValid;
	private BindingResult errorLog;
	
	@Before
	public void setUp() throws Exception {
		catValid = new CategoryValidator();
		category = new Category("Category");
		errorLog = new BindException(category, "Category");
	}

	@After
	public void tearDown() throws Exception {
		errorLog = new BindException(category, "Category");
	}

	@Test
	public void testSupports() {
		assertTrue(catValid.supports(category.getClass()));
		assertFalse(catValid.supports(Random.class));
	}

	@Test
	public void shouldHasTypeError() {
		catValid.validate(new Random(), errorLog);
		boolean typeErrorFound = hasError(errorLog.getAllErrors(), "type_error");
		assertTrue(typeErrorFound);
	}
	
	@Test
	public void shouldHasNullError() {
		catValid.validate(null, errorLog);
		assertTrue(hasError(errorLog.getAllErrors(), "null_error"));
	}
	
	@Test
	public void shouldHasntTypeError() {
		catValid.validate(category, errorLog);
		boolean typeErrorFound = hasError(errorLog.getAllErrors(), "type_error");
		assertFalse(typeErrorFound);
	}
	
	@Test
	public void shouldHasExistError() {
		Category pierwsza = new Category("Pierwsza");
		errorLog = new BindException(pierwsza, "pierwsza");
		catValid.validate(pierwsza, errorLog);
		
		assertTrue(hasError(errorLog.getAllErrors(), "exists_error"));
	}

	@Test
	public void shouldHasEmptyError() {
		Category empty = new Category("               ");
		errorLog = new BindException(empty, "empty");
		catValid.validate(empty, errorLog);
		
		assertTrue(hasError(errorLog.getAllErrors(), "empty_error"));
	}
	
	@Test
	public void shouldToShort() {
		Category oneLetter = new Category("s");
		errorLog = new BindException(oneLetter, "oneLetter");
		catValid.validate(oneLetter, errorLog);
		
		assertTrue(hasError(errorLog.getAllErrors(), "size_error"));
	}
	
	@Test
	public void shouldToLong() {
		Category toLong = new Category();
		errorLog = new BindException(toLong, "toLong");
		String name = "";
		for (int i = 0; i < 36; i++) {
			name += "a";
		}
		assertTrue(name.length() == 36);
		
		toLong.setName(name);
		catValid.validate(toLong, errorLog);
	}
	
	@Test
	public void shouldHasDigitError() {
		Category digits = new Category("1234");
		errorLog = new BindException(digits, "digits");
		catValid.validate(digits, errorLog);
		
		assertTrue(hasError(errorLog.getAllErrors(), "only_digits_error"));
	}
	
	@Test
	public void shouldNotHasDigitError() {
		Category notDigits = new Category("1234m");
		errorLog = new BindException(notDigits, "digits");
		catValid.validate(notDigits, errorLog);
		
		assertFalse(hasError(errorLog.getAllErrors(), "only_digits_error"));
	}
	
	private boolean hasError(List<ObjectError> errors, String code) {
		for (ObjectError error : errors) {
			if (error.getCode().equals(code))
				return true;
		}
		return false;
	}
}
