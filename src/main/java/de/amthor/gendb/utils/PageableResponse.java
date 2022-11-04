package de.amthor.gendb.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import de.amthor.gendb.payload.PageableResponseDto;

/**
 * Builder based wrapper to generate a pageable response from a list retrieved from a repository.
 * The class encapsulates entirely all steps to set up the page response requested by a usual spring pageable request.
 * The builder returns the proper response. The effective list is passed in by a lambda function.
 * 
 * The response page DTO needs to extend the PageableResponseDto.
 * 
 * @see PageableResponseDto
 * 
 * @author axel
 *
 */
public class PageableResponse {

	private static final Logger LOGGER = LoggerFactory.getLogger(PageableResponse.class);
	
	private ModelMapper mapper;
	private PageableResponseDto pageResponseDto;
	private Page<?> responsePage;
	private Class<?> dtoType;
	
	@FunctionalInterface
	public interface PageList {
		Page<?> page(Pageable pagelist);
	}
	
	public static class Builder {
		
		private ModelMapper mapper;
		private int pageNo;
		private int pageSize;
		private String sortBy;
		private String sortDir;
		private PageableResponseDto pageResponseDto;
		private Page<?> responsePage;
		private Class<?> ElementDtoType;
		
		/**
		 * Sets the general object model mapper
		 * @param mapper
		 * @return
		 */
		public Builder mapper(ModelMapper mapper) {
			this.mapper = mapper;
			return this;
		}
		
		public Builder pageNo(int pageNo) {
			this.pageNo = pageNo;
			return this;
		}
		

		public Builder pageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}
		

		public Builder sortBy(String sortBy) {
			this.sortBy = sortBy;
			return this;
		}
		

		public Builder sortDir(String sortDir) {
			this.sortDir = sortDir;
			return this;
		}
		
		/**
		 * Sets the enclosing response object dto
		 * 
		 * @param pageResponse
		 * @return
		 */
		public Builder responseDto(PageableResponseDto pageResponse) {
			this.pageResponseDto = pageResponse;
			return this;
		}
		
		/**
		 * Lambda function to retrieve the list of elements as pageable list.
		 * 
		 * @param pagelist
		 * @return
		 */
		public Builder setPage(PageList pagelist) {
			Pageable pageable = getPageable();
			this.responsePage = pagelist.page(pageable);
			return this;
		}
		
		/**
		 * Sets the type of element objects in a pages elements list
		 * @param ElementDtoType
		 * @return
		 */
		public Builder setElementType(Class<?> ElementDtoType) {
			this.ElementDtoType = ElementDtoType;
			return this;
		}
		
		/**
		 * Build the page response and transforms it to the given response object
		 * 
		 * @param <t>
		 * @return
		 */
		public <t> t build() {
			PageableResponse gpr = new PageableResponse();
			gpr.setMapper(mapper);
			gpr.setPageResponseDto(pageResponseDto);
			gpr.setResponsePage(responsePage);
			gpr.setElementDtoType(ElementDtoType);
			
			return gpr.createPageResponse(this.ElementDtoType);
		}
		
		private Pageable getPageable() {
			Pageable pageable;
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
	                : Sort.by(sortBy).descending();
			// create pageable instance
	        pageable = PageRequest.of(pageNo, pageSize, sort);
			return pageable;
		}

	};
	
	
    /**
     * Generic simple object mapper
     * 
     * @param <To> Target class
     * @param <From> Source class 
     * @param source Source
     * @param type Target type
     * @return mapped object
     */
    public <To, From> To genericSimpleMapper(From source, Class<To>type) {
    	To target = source != null ? mapper.map(source, type) : null;
        return target;
    }
    
	/**
	 * @param <ENT>
	 * @param <DTO>
	 * @param page
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <ENT, DTO, t> t createPageResponse(Class<DTO>dto) {
		// get content for page object
		Page<ENT> page = (Page<ENT>) this.responsePage;
        List<ENT> listOfEntities = page.getContent();

        List<DTO> content = listOfEntities.stream().map(entity -> { 
        		LOGGER.debug("=================> " + entity.toString()); 
        		return genericSimpleMapper(entity, dto); 
        	}).collect(Collectors.toList());

        pageResponseDto.setElements(content);
        pageResponseDto.setPageNo(page.getNumber());
        pageResponseDto.setPageSize(page.getSize());
        pageResponseDto.setTotalElements(page.getTotalElements());
        pageResponseDto.setTotalPages(page.getTotalPages());
        pageResponseDto.setLast(page.isLast());
        
		return (t) pageResponseDto;
	}

	/**
	 * Object Mapper
	 * 
	 * @param mapper the mapper to set
	 */
	private void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * @param pageResponseDto the pageResponseDto to set
	 */
	private void setPageResponseDto(PageableResponseDto pageResponseDto) {
		this.pageResponseDto = pageResponseDto;
	}

	/**
	 * @param responsePage the responsePage to set
	 */
	private void setResponsePage(Page<?> responsePage) {
		this.responsePage = responsePage;
	}

	public Class<?> getDtoType() {
		return dtoType;
	}

	public void setElementDtoType(Class<?> dtoType) {
		this.dtoType = dtoType;
	}
}
