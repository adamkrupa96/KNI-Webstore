package kni.webstore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SubCategory implements Serializable {
	
	private static final long serialVersionUID = -9051937994817642175L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@OneToMany(cascade= CascadeType.MERGE, mappedBy="subCategory", fetch=FetchType.EAGER, orphanRemoval=false)
	private List<Product> products;
	
	@JsonIgnore
	@ManyToOne(optional=false)
	@JoinColumn(name="category_id")
	private Category category;
	
	public SubCategory(String name, List<Product> products) {
		this.name = name;
		this.products = products;
	}
	
	public SubCategory(String name) {
		this.name = name;
		this.products = new ArrayList<Product>();
	}
	
	public SubCategory() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addProduct(Product product) {
		if (this.products.contains(product)) return;
		
		this.products.add(product);
		product.setSubCategory(this);
	}
	
	public void removeProduct(Product product) {
		this.products.remove(product);
		product.setSubCategory(null);
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "SubCategory [id=" + id + ", name=" + name + ", products=" + products + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubCategory other = (SubCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
