package de.amthor.gendb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.Coltype;

import java.util.Optional;

public interface ColtypesRepository extends JpaRepository<Coltype, Long> {
	
    Optional<Coltype> findByTypeAndDbtype(String type, String dbtype);

	boolean existsByTypeAndDbtype(String type, String dbtype);

	Page<Collation> findAllByDbtype(Pageable pageable, String dbtype);
	

}
