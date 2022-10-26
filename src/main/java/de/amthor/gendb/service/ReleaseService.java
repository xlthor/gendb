/**
 * 
 */
package de.amthor.gendb.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;

import de.amthor.gendb.entity.Release;
import de.amthor.gendb.payload.ReleaseDto;
import de.amthor.gendb.payload.ReleaseResponse;

/**
 * @author axel
 *
 */
public interface ReleaseService {

	/**
	 * Create a new release for a project. 
	 * The project must be owned by the current user
	 * 
	 * @param releasedto
	 * @return
	 */
	public ReleaseDto createRelease(@Valid ReleaseDto releasedto);
	
	/**
	 * Update an existing release.
	 * The parent project must be owned by the current user.
	 * 
	 * @param releasedto
	 * @return
	 */
	public ReleaseDto updateRelease(@Valid ReleaseDto releasedto);
	
	public void deleteRelease(long id);
	
	/**
	 * Retrieve a release by its ID.
	 * The project must be owned by the current user
	 * 
	 * @param releaseid
	 * @return
	 */
	public Optional<Release> getReleaseById(long releaseid);
	
	/**
	 * Retrieve all releases of a prject which is owned by the current user.
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sort
	 * @param projectId
	 * @return
	 */
	public ReleaseResponse getAllReleases(int pageNo, int pageSize, String sortBy, String sortDir, long projectId);

}
