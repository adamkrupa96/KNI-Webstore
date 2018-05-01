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

	@Autowired
	private ProductService prodService;
	
	@Override
	public void validate(Object objProduct, Errors errors) {
		Product productToValidate = null;
		System.out.println("validating");
		if (supports(objProduct.getClass())) productToValidate = (Product) objProduct;
		else {
			errors.reject("type_error");
			return;
		}
		
		String name = productToValidate.getName();
		name = Optional.ofNullable(name).orElse("");
		
		String brand = productToValidate.getBrand();
		String model = productToValidate.getModel();
		String shortDesc = productToValidate.getShortDescription();
		shortDesc = Optional.ofNullable(shortDesc).orElse("");
		
		String longDesc = productToValidate.getLongDescription();
		longDesc = Optional.ofNullable(longDesc).orElse("");
		
		int stock = productToValidate.getInStock();
		double price = productToValidate.getPrice();
		
		if ( !hasLengthInInterval(4, 64, name) ) errors.rejectValue("name", "name_size");
		if ( !hasLengthInInterval(2, 16, brand) ) errors.rejectValue("brand", "brand_size");
		if ( !hasLengthInInterval(2, 32, model) ) errors.rejectValue("model", "model_size");
		if ( !hasLengthInInterval(0, 96, shortDesc) ) errors.rejectValue("shortDescription", "shortDesc_size");
		if ( !hasLengthInInterval(0, 256, longDesc) ) errors.rejectValue("longDescription", "longDesc_size");
		
		if (stock < 0) errors.rejectValue("inStock", "stock_negative");
		if (price < 0) errors.rejectValue("price", "price_negative");
		if (exists(productToValidate)) errors.reject("exists_error");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Product.class);
	}
	
	public boolean exists(Product product) {
		List<Product> allProducts = prodService.getAllProducts();
	
		return allProducts.stream().anyMatch(eachProd -> {
			String validatingProductBrand = product.getBrand().trim().toUpperCase();
			String validatingProductModel = product.getModel().trim().toUpperCase();
			
			String eachProdBrand = eachProd.getBrand().trim().toUpperCase();
			String eachProdModel = eachProd.getModel().trim().toUpperCase();
			
			return eachProdBrand.equals(validatingProductBrand) 
					&& eachProdModel.equals(validatingProductModel);
		});
	}
	
	private boolean hasLengthInInterval(int min, int max, String str) {
		int length = str.length();
		return length >= min && length <= max;
	}
}
