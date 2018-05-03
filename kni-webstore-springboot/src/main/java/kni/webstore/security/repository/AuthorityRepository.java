package kni.webstore.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kni.webstore.security.model.Authority;
import kni.webstore.security.model.AuthorityEnum;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(AuthorityEnum name);
}
