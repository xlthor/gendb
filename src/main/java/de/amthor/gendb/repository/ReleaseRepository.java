/**
 * 
 */
package de.amthor.gendb.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.Project;
import de.amthor.gendb.entity.Release;
import de.amthor.gendb.entity.User;

/**
 * @author axel
 *
 */
public interface ReleaseRepository extends JpaRepository<Release, Long> {

	Optional<Release> findById(long id);

//	Page<Release> findAll(Pageable pageable, Set<User> users);

//	Project findByIdAndUsersIn(Long id, Set<User> users);
	
}
