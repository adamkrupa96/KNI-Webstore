package kni.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kni.webstore.model.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
	Feature findByName(String name);
}
