/**
 * 
 */
package de.amthor.gendb.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.Release;

/**
 * @author axel
 *
 */
public interface ReleaseRepository extends JpaRepository<Release, Long> {

	Optional<Release> findById(long releaseid);

	Optional<Release> findByVersion(String version);

	Page<Release> findByProjectId(Pageable pageable, long projectId);
}
