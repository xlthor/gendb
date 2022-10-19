/**
 * 
 */
package de.amthor.gendb.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.amthor.gendb.entity.Project;
import de.amthor.gendb.entity.Release;
import de.amthor.gendb.entity.User;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.payload.ProjectResponse;
import de.amthor.gendb.repository.ProjectRepository;
import de.amthor.gendb.repository.ReleaseRepository;
import de.amthor.gendb.repository.UserRepository;
import de.amthor.gendb.service.ProjectService;

/**
 * @author axel
 *
 */
@Service
public class ProjectServiceImpl extends ServiceBase implements ProjectService {
	
	private ProjectRepository projectRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private ReleaseRepository releaseRepository;
	

	public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper mapper) {
		super(mapper);
        this.projectRepository = projectRepository;
	}
	
	/**
	 * Create a new Database Project
	 */
	@Override
	public ProjectDto createProject(@Valid ProjectDto projectDto) {

		
		// convert DTO to entity
        Project project = genericSimpleMapper(projectDto, Project.class);
        
        Project newProject = projectRepository.save(project);

        // convert entity to DTO
        ProjectDto projectResponse = genericSimpleMapper(newProject, ProjectDto.class);
        return projectResponse;
        
	}

	@Override
	public ProjectDto getProjectById(long id) {
		Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
		return genericSimpleMapper(project, ProjectDto.class);
	}

	@Override
	public ProjectDto updateProject(ProjectDto projectDto, long id) {
		
		Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
		
		project.setDescription(projectDto.getDescription());
		project.setName(projectDto.getName());
		project.setId(id);
		
		/*
		 * set the provided users, if any. This will overwrite the current list of users
		 * Deleting all users is not possible (empty list)
		 * As we are not cascading, it will only update the reference, not the user itself.
		 */
		// listOfProjects.stream().map(project -> genericSimpleMapper(project, ProjectDto.class)).collect(Collectors.toList());
		Set<User> requestedUsers = projectDto.getUsers().stream().map(userDto -> genericSimpleMapper(userDto, User.class)).collect(Collectors.toSet());
		if ( requestedUsers != null && !requestedUsers.isEmpty() )
		{
			for ( User rUser : requestedUsers ) {
				// check if valid user
				if ( !userRepository.existsById(rUser.getId()) )
					throw new ResourceNotFoundException("User", "id", rUser.getId());
			}
			
			// update
			project.setUsers(requestedUsers);
		}
		
		/*
		 * set the list of current releases. This will only ADD releases, 
		 * it will not remove any release as this would lead to an orphan release!
		 * As we are not cascading, this will only update the reference, not the release itself.
		 */
		Set<Release> requestedReleases = projectDto.getReleases().stream().map(releaseDto -> genericSimpleMapper(releaseDto, Release.class)).collect(Collectors.toSet());
		if ( requestedReleases != null && !requestedReleases.isEmpty() )
		{
			for ( Release rRelease : requestedReleases ) {
				if ( !releaseRepository.existsById(rRelease.getReleaseid()) )
						throw new ResourceNotFoundException("Release", "id", rRelease.getReleaseid());
				
				// adds only non-existing references
				project.getReleases().add(rRelease);
			}
			
			// update
			project.setReleases(project.getReleases());
		}
		
		// persist
		Project updatedProject = projectRepository.save(project);
		
		return genericSimpleMapper(updatedProject, ProjectDto.class);
	}

	@Override
	public void deleteProjectById(long id) {
		Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
		projectRepository.delete(project);
		
		/*
		 * FIXME: we need to delete all subsequent objects one by one!
		 * 
		 * 	delete the releases
		 * 		delete the databases of each release
		 * 			delete the tables of each database
		 * 				delete the columns of each table
		 * 					...
		 * 
		 */
	}

	@Override
	public ProjectResponse getAllProjects(int pageNo, int pageSize, String sortBy, String sortDir, Set<User> users) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // get projects of this user
		Page<Project> projects = projectRepository.findByUsersIn(pageable, users);
		
        // get content for page object
        List<Project> listOfProjects = projects.getContent();

        List<ProjectDto> content = listOfProjects.stream().map(project -> genericSimpleMapper(project, ProjectDto.class)).collect(Collectors.toList());

        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setProjects(content);
        projectResponse.setPageNo(projects.getNumber());
        projectResponse.setPageSize(projects.getSize());
        projectResponse.setTotalElements(projects.getTotalElements());
        projectResponse.setTotalPages(projects.getTotalPages());
        projectResponse.setLast(projects.isLast());

        return projectResponse;
        
	}
	
	@Override
	public ProjectDto getProjectByIdAndUser(Long projectId, Set<User> users) {

		Project project = projectRepository.findByIdAndUsersIn(projectId, users);
				
		return genericSimpleMapper(project, ProjectDto.class);
	}

	@Override
	public Optional<ProjectDto> getProjectByReleaseAndUser(long releaseId, User user) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
