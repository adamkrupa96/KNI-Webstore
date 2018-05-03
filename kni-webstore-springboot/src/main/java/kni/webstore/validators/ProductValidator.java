package kni.webstore.validators;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kni.webstore.model.Product;
import kni.webstore.service.ProductService;

@Component
public class ProductValidator implements Validator {

	private static int NAME_MIN = 4;
	private static int NAME_MAX = 64;
	private static String NAME_MSG = "Długość nazwy min. " + NAME_MIN + " i max. " + NAME_MAX + " znaki.";
	
	private static int BRAND_MIN = 2;
	private static int BRAND_MAX = 16;
	private static String BRAND_MSG = "Długość nazwy marki min. " + BRAND_MIN + " i max. " + BRAND_MAX + " znaki.";
	
	private static int MODEL_MIN = 2;
	private static int MODEL_MAX = 32;
	private static String MODEL_MSG = "Długość nazwy modelu min. " + MODEL_MIN + " i max. " + MODEL_MAX + " znaki.";
	
	private static int SHORT_DESC_MAX = 96;
	private static String SHORT_DESC_MSG = "Długość krótkiego opisu max. " + SHORT_DESC_MAX + " znaki.";
	
	private static int LONG_DESC_MAX = 256;
	private static String LONG_DESC_MSG = "Długość długiego opisu max. " + LONG_DESC_MAX + " znaki.";
	
	@Autowired
	private ProductService prodService;
	
	@Override
	public void validate(Object objProduct, Errors errors) {
		Product productToValidate = null;
		if (supports(objProduct.getClass())) productToValidate = (Product) objProduct;
		else {
			errors.reject("type_error");
			return;
		}
		checkFieldsLength(errors, productToValidate);
		checkNumberFields(errors, productToValidate);
		rejectIfProductExists(errors, productToValidate);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Product.class);
	}
	
	private void checkFieldsLength(Errors errors, Product productToValidate) {
		String name = productToValidate.getName();
		name = Optional.ofNullable(name).orElse("");
		
		String brand = productToValidate.getBrand();
		String model = productToValidate.getModel();
		String shortDesc = productToValidate.getShortDescription();
		shortDesc = Optional.ofNullable(shortDesc).orElse("");
		
		String longDesc = productToValidate.getLongDescription();
		longDesc = Optional.ofNullable(longDesc).orElse("");
		
		if ( !hasLengthInInterval(NAME_MIN, NAME_MAX, name) ) errors.rejectValue("name", "name_size", NAME_MSG);
		if ( !hasLengthInInterval(BRAND_MIN, BRAND_MAX, brand) ) errors.rejectValue("brand", "brand_size", BRAND_MSG);
		if ( !hasLengthInInterval(MODEL_MIN, MODEL_MAX, model) ) errors.rejectValue("model", "model_size", MODEL_MSG);
		if ( !hasLengthInInterval(0, SHORT_DESC_MAX, shortDesc) ) errors.rejectValue("shortDescription", "shortDesc_size", SHORT_DESC_MSG);
		if ( !hasLengthInInterval(0, LONG_DESC_MAX, longDesc) ) errors.rejectValue("longDescription", "longDesc_size", LONG_DESC_MSG);
	}

	private void checkNumberFields(Errors errors, Product productToValidate) {
		int stock = productToValidate.getInStock();
		double price = productToValidate.getPrice();

		if (stock < 0) errors.rejectValue("inStock", "stock_negative");
		if (price < 0) errors.rejectValue("price", "price_negative");
	}
	
	private void rejectIfProductExists(Errors errors, Product product) {
		if (exists(product)) {
			errors.reject("exists_error");
		}
	}
	
	private boolean exists(Product product) {
		List<Product> allProducts = prodService.getAllProducts();
	
		return allProducts.stream().anyMatch(eachProd -> {
			String validatingProductBrand = product.getBrand().trim().toUpperCase();
			String validatingProductModel = product.getModel().trim().toUpperCase();
			
			String eachProdBrand = eachProd.getBrand().trim().toUpperCase();
			String eachProdModel = eachProd.getModel().trim().toUpperCase();
			
			return eachProdBrand.equals(validatingProductBrand) 
					&& eachProdModel.equals(validatingProductModel);
		});

// Dla testu jednostkowego
//		return product.getBrand().equalsIgnoreCase("ASUS") && product.getModel().equalsIgnoreCase("ROG");
	}
	
	private boolean hasLengthInInterval(int min, int max, String str) {
		int length = str.length();
		return length >= min && length <= max;
	}
}
