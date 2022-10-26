package de.amthor.gendb.payload;

import java.util.List;

import de.amthor.gendb.utils.PageableResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic page response DTO. Any class method which returns a pageable response by using the PageableResponse builder needs a particular response DTO which 
 * extends this generic DTO. This DTO must not have a @Data annotation and inherit the protected properties from this class. In order to typecast the serialized elements list, 
 * the @JsonProperty annotation should be used as otherwise it is named "elements" which might not be useful.
 * 
 * @see PageableResponse
 * @author axel
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PageableResponseDto {

	protected List<?> elements;
	
    protected int pageNo;
	
    protected int pageSize;
	
    protected long totalElements;
	
    protected int totalPages;
	
    protected boolean last;
}
