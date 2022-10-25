package de.amthor.gendb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.Tableformat;

public interface FormatRepository extends JpaRepository<Tableformat, Long> {

	boolean existsByFormatnameAndTypename(String formatname, String typename);

}
