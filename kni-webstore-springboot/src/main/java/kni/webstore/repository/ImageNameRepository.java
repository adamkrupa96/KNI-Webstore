package kni.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kni.webstore.model.ImageName;

@Repository
public interface ImageNameRepository extends JpaRepository<ImageName, Long> {

}
