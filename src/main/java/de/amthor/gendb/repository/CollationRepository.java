package de.amthor.gendb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.Collation;

import java.util.Optional;

public interface CollationRepository extends JpaRepository<Collation, Long> {
	
    Optional<Collation> findByCollation(String name);

	boolean existsByCollation(String name);

	boolean existsByCollationAndTypename(String collation, String typename);

	Page<Collation> findAllByTypename(Pageable pageable, String typename);
	

}
