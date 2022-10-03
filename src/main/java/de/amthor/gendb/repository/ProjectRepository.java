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
import de.amthor.gendb.entity.User;

/**
 * @author axel
 *
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

	Optional<Project> findById(long id);

	Page<Project> findByUsersIn(Pageable pageable, Set<User> users);

	Project findByIdAndUsersIn(Long id, Set<User> users);
	
}
