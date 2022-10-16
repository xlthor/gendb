package de.amthor.gendb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.DbType;

import java.util.Optional;

public interface DBTypeRepository extends JpaRepository<DbType, Long> {
	
    Optional<DbType> findByTypename(String typename);

	boolean existsByTypename(String typename);

	Optional<DbType> findByTypenameAndVersion(String dbTypename, String version);

}
