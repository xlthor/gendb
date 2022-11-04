package de.amthor.gendb.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.Columns;

public interface ColumnsRepository  extends JpaRepository<Columns, Long> {

	Optional<Columns> findByColnameAndTableid(String colname, long tableid);

	Page<Columns> findAllByTableid(Pageable pageable, Long tableid);

	Optional<Columns> findByColname(String colname);
	

}
