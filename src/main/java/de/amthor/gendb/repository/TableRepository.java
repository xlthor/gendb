package de.amthor.gendb.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.GdbTable;


public interface TableRepository extends JpaRepository<GdbTable, Long> {

	Optional<GdbTable> findByTablenameAndDatabaseId(String tablename, long databaseId);

	Page<GdbTable> findAllByDatabaseId(Pageable pageable, long databaseId);

}
