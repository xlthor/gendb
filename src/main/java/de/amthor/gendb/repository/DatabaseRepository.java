package de.amthor.gendb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.Database;

import java.util.Optional;

public interface DatabaseRepository extends JpaRepository<Database, Long> {
	
    Optional<Database> findByDbname(String name);

	boolean existsByDbname(String name);

	Page<Database> findAllByReleaseId(Pageable pageable, long releaseId);

}
