package de.amthor.gendb.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.ColumnParameter;

public interface ColumnParamRepository  extends JpaRepository<ColumnParameter, Long> {
	
	// keys may have multiple values
	Set<ColumnParameter> findAllByParamkeyAndDbtype(String paramkey, String dbtype);

	boolean existsByParamkeyAndParamvalueAndDbtype(String paramkey, String paramvalue, String typename);

}
