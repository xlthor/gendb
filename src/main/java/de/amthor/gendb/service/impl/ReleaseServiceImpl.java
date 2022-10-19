/**
 * 
 */
package de.amthor.gendb.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.amthor.gendb.entity.Release;
import de.amthor.gendb.exception.AlreadyExistsException;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.payload.ReleaseDto;
import de.amthor.gendb.payload.ReleaseResponse;
import de.amthor.gendb.repository.ReleaseRepository;
import de.amthor.gendb.service.ReleaseService;

/**
 * @author axel
 *
 */
@Service
public class ReleaseServiceImpl implements ReleaseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseServiceImpl.class);
	
	private ReleaseRepository releaseRepository;
	
	private ModelMapper mapper;
	
	
	public ReleaseServiceImpl(ReleaseRepository releaseRepository, ModelMapper mapper) {
		this.releaseRepository = releaseRepository;
		this.mapper = mapper;
	}
	
	
	@Override
	public ReleaseDto createRelease(@Valid ReleaseDto releasedto) {
		
		// convert DTO to entity
        Release release = mapToEntity(releasedto);
        
        releaseRepository.findByVersion(releasedto.getVersion()).ifPresent(
        		s -> {throw new AlreadyExistsException("Release", "Version", releasedto.getVersion());});
        
        Release newRelease = releaseRepository.save(release);

        // convert entity to DTO
        ReleaseDto releaseResponse = mapToDTO(newRelease);
        return releaseResponse;
	}

	@Override
	public ReleaseDto updateRelease(@Valid ReleaseDto releaseDto) {
		
		Optional<Release> release = getReleaseById(releaseDto.getReleaseid());
		if ( release.isPresent() ) {
			
			/*
			 * Check for a new duplicate:
			 * If the dto contains a different version than the current persisted release,
			 * check if that new dto version is already existing
			 */
			if ( !releaseDto.getVersion().equals(release.get().getVersion() )) {
				releaseRepository.findByVersion(releaseDto.getVersion()).ifPresent (
						s -> {throw new AlreadyExistsException("Release", "Version", releaseDto.getVersion());});
			}
			
			Release releaseUpdate = mapToEntity(releaseDto);
			Release newRelease = releaseRepository.save(releaseUpdate);
			// convert entity to DTO
	        ReleaseDto releaseResponse = mapToDTO(newRelease);
	        return releaseResponse;
		}
		else 
			throw new ResourceNotFoundException("Release", "id", releaseDto.getReleaseid());
		
	}

	@Override
	public void deleteRelease(long id) {
		
		Optional<Release> release = releaseRepository.findById(id);
		
		if ( release.isPresent() )
			releaseRepository.deleteById(id);
	}

	@Override
	public Optional<Release> getReleaseById(long id) {
		Optional<Release> release = releaseRepository.findById(id);
		
		if ( release.isPresent() )
			return release;
		else
			return Optional.empty();
	}

	@Override
	public ReleaseResponse getAllReleases(int pageNo, int pageSize, Sort sort, long projectId) {
		
		// create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        
		Page<Release> releases = releaseRepository.findByProjectId(pageable, projectId);
		
		// get content for page object
        List<Release> listOfReleases = releases.getContent();

        LOGGER.info(Arrays.toString(listOfReleases.toArray()));
        
        List<ReleaseDto> content = listOfReleases.stream().map(release -> mapToDTO(release)).collect(Collectors.toList());
        
        LOGGER.info(Arrays.toString(content.toArray()));
        
		ReleaseResponse releaseResponse = new ReleaseResponse();
        releaseResponse.setReleases(content);
        releaseResponse.setPageNo(releases.getNumber());
        releaseResponse.setPageSize(releases.getSize());
        releaseResponse.setTotalElements(releases.getTotalElements());
        releaseResponse.setTotalPages(releases.getTotalPages());
        releaseResponse.setLast(releases.isLast());

        return releaseResponse;
	}
	
    // convert Entity into DTO
    private ReleaseDto mapToDTO(Release release){
    	ReleaseDto rlDto = release != null ? mapper.map(release, ReleaseDto.class) : null;
        return rlDto;
    }
    
    // convert DTO to entity
    private Release mapToEntity(ReleaseDto rlDto){
    	Release release = rlDto != null ? mapper.map(rlDto, Release.class) : null;
        return release;
    }


}
