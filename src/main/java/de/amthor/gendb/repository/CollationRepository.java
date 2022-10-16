package de.amthor.gendb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.DbType;

import java.util.Optional;

public interface CollationRepository extends JpaRepository<Collation, Long> {
	
    Optional<DbType> findByCollation(String name);

	boolean existsByCollation(String name);

	boolean existsByCollationAndTypename(String collation, String typename);
	

}
