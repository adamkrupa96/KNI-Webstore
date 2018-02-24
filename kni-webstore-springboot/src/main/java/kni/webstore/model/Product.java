package kni.webstore.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product implements Serializable {
	
	private static final long serialVersionUID = 4734523254250446715L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String brand;
	private String model;
	private double price;
	private int inStock;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.PERSIST, optional=true)
	private SubCategory subCategory;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="product_id")
	private Set<Feature> features;
	
	public Product(String brand, String model, double price, int inStock, SubCategory subCategory,
			Set<Feature> features) {
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.inStock = inStock;
		this.subCategory = subCategory;
		this.features = features;
	}
	
	public Product() {
	}
	
	public void addFeature(String name, String value) {
		Feature feature = new Feature(name, value);
		this.features.add(feature);
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getInStock() {
		return inStock;
	}


	public void setInStock(int inStock) {
		this.inStock = inStock;
	}


	public SubCategory getSubCategory() {
		return subCategory;
	}


	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}


	public Set<Feature> getFeatures() {
		return features;
	}


	public void setFeatures(Set<Feature> features) {
		this.features = features;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", brand=" + brand + ", model=" + model + ", price=" + price + ", inStock="
				+ inStock +  ", features=" + features + "]";
	}
}
