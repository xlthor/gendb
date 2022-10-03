package de.amthor.gendb.service;

import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import de.amthor.gendb.entity.User;
import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.payload.ProjectResponse;
>
public interface ProjectService {

	/**
	 * Create a new Database Project
	 * @param projectDto
	 * @return
	 */
	ProjectDto createProject(@Valid ProjectDto projectDto);
	
	/**
	 * Get a particular project by its ID
	 * 
	 * @param id
	 * @return
	 */
	ProjectDto getProjectById(long id);
	
	/**
	 * Update the project. Method also used to add / remove project users
	 * 
	 * 
	 * @param project
	 * @param id
	 * @return
	 */
	ProjectDto updateProject(ProjectDto project, long id);
	
	/**
	 * Delete cascade this project
	 * 
	 * @param id
	 */
	void deleteProjectById(long id);
	
	/**
	 * Get a pageable list of all projects of the current user
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @param users
	 * @return
	 */
	ProjectResponse getAllProjects(int pageNo, int pageSize, String sortBy, String sortDir, Set<User> users);

	/**
	 * Find a project by ID and User. Only if the according project is owned by this user it is returned, otherwise null.
	 * 
	 * @param projectId
	 * @param user
	 * @return
	 */
	ProjectDto getProjectByIdAndUser(Long projectId, Set<User> users);

}
