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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.amthor.gendb.entity.Project;
import de.amthor.gendb.entity.User;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.payload.ProjectResponse;
import de.amthor.gendb.repository.ProjectRepository;
import de.amthor.gendb.service.ProjectService;

/**
 * @author axel
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {
	
	private ProjectRepository projectRepository;
	
	private ModelMapper mapper;

	public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper mapper) {
        this.projectRepository = projectRepository;
        this.mapper = mapper;
  }
	
	/**
	 * Create a new Database Project
	 */
	@Override
	public ProjectDto createProject(@Valid ProjectDto projectDto) {

		
		// convert DTO to entity
        Project project = mapToEntity(projectDto);
        
        Project newProject = projectRepository.save(project);

        // convert entity to DTO
        ProjectDto projectResponse = mapToDTO(newProject);
        return projectResponse;
        
	}

	@Override
	public ProjectDto getProjectById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjectDto updateProject(ProjectDto project, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProjectById(long id) {
		Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
		projectRepository.delete(project);		
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

        List<ProjectDto> content = listOfProjects.stream().map(project -> mapToDTO(project)).collect(Collectors.toList());

        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setProjects(content);
        projectResponse.setPageNo(projects.getNumber());
        projectResponse.setPageSize(projects.getSize());
        projectResponse.setTotalElements(projects.getTotalElements());
        projectResponse.setTotalPages(projects.getTotalPages());
        projectResponse.setLast(projects.isLast());

        return projectResponse;
        
	}
	
    // convert Entity into DTO
    private ProjectDto mapToDTO(Project project){
    	ProjectDto prjDto = mapper.map(project, ProjectDto.class);
        return prjDto;
    }

    // convert DTO to entity
    private Project mapToEntity(ProjectDto prjDto){
    	Project project = mapper.map(prjDto, Project.class);
        return project;
    }

	@Override
	public ProjectDto getProjectByIdAndUser(Long projectId, Set<User> users) {

		Project project = projectRepository.findByIdAndUsersIn(projectId, users);
				
		return mapToDTO(project);
	}

}
