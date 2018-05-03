package kni.webstore.validator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;

import kni.webstore.validators.ProductValidator;
import kni.webstore.model.*;

public class ProductValidatorTest {

	private ProductValidator prodValid;
	private Product product;
	private BindException errorLog;
	
	
	@Before
	public void setUp() throws Exception {
		prodValid = new ProductValidator();
		product = new Product("12", "12", 0.00, 0);
		product.setName("12 12");
		errorLog = getCleanErrorLog(product);
	}

	@Test
	public void typeError() {
		prodValid.validate(new Object(), errorLog);
		assertTrue(hasError("type_error"));
	}
	
	@Test
	public void existsError() {
		product.setBrand("ASuS");
		product.setModel("RoG");
		
		prodValid.validate(product, errorLog);
		assertTrue(hasError("exists_error"));
	}
	
	@Test
	public void notExists() {
		product.setBrand("elO");
		product.setModel("HEHE");
		
		prodValid.validate(product, errorLog);
		assertFalse(hasError("exists_error"));
	}
	
	@Test
	public void minsTest() {
		prodValid.validate(product, errorLog);
		assertFalse(errorLog.hasErrors());	
	}
	
	@Test
	public void maxsTest() {
		String maxBrandLength = getStringOfLength(16);
		assertTrue(maxBrandLength.length() == 16);
		
		String maxModelLength = getStringOfLength(32);
		assertTrue(maxModelLength.length() == 32);
		
		int stock = Integer.MAX_VALUE;
		double price = Double.MAX_VALUE;
		
		String maxShortDesc = getStringOfLength(96);
		assertTrue(maxShortDesc.length() == 96);
		
		String maxLongDesc = getStringOfLength(256);
		assertTrue(maxLongDesc.length() == 256);
		
		product.setName(maxBrandLength + " " + maxModelLength);
		product.setBrand(maxBrandLength);
		product.setModel(maxModelLength);
		product.setLongDescription(maxLongDesc);
		product.setShortDescription(maxShortDesc);
		product.setInStock(stock);
		product.setPrice(price);
		
		prodValid.validate(product, errorLog);
		assertFalse(errorLog.hasErrors());
	}
	
	@Test
	public void overTest() {
		product.setName(getStringOfLength(65));
		product.setBrand(getStringOfLength(17));
		product.setModel(getStringOfLength(33));
		product.setLongDescription(getStringOfLength(257));
		product.setShortDescription(getStringOfLength(97));
		
		prodValid.validate(product, errorLog);
		
		assertTrue(hasError("name_size"));
		assertTrue(hasError("brand_size"));
		assertTrue(hasError("model_size"));
		assertTrue(hasError("longDesc_size"));
		assertTrue(hasError("shortDesc_size"));
	}
	
	@Test
	public void negativeTest() {
		product.setName(getStringOfLength(1));
		product.setBrand(getStringOfLength(1));
		product.setModel(getStringOfLength(1));
		product.setInStock(-10);
		product.setPrice(-100.00);
		
		prodValid.validate(product, errorLog);
		
		assertTrue(hasError("name_size"));
		assertTrue(hasError("brand_size"));
		assertTrue(hasError("model_size"));
		assertTrue(hasError("stock_negative"));
		assertTrue(hasError("price_negative"));
	}

	private String getStringOfLength(int length) {
		String str = "";
		for (int i = 0; i < length; i++) {
			str += "a";
		}
		return str;
	}
	
	private BindException getCleanErrorLog(Product prod) {
		return new BindException(prod, "prod");
	}
	
	private boolean hasError(String code) {
		return errorLog.getAllErrors().stream().anyMatch(error -> error.getCode().equals(code));
	}
}
