package kni.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kni.webstore.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category getByName(String name);
	void deleteByName(String name);
}
