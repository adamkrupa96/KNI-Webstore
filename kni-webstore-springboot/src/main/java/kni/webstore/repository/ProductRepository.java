package kni.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kni.webstore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
