/**
 * 
 */
package de.amthor.gendb.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author axel
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseResponse extends PageableResponseDto {

	@JsonView({Views.Response.class, Views.DatabaseResponse.class})
	@JsonProperty("releases")
    List<ReleaseDto> elements;
	
	@JsonView(Views.Response.class)
    int pageNo;
	
	@JsonView(Views.Response.class)
    int pageSize;
	
	@JsonView(Views.Response.class)
    long totalElements;
	
	@JsonView(Views.Response.class)
    int totalPages;
	
	@JsonView(Views.Response.class)
    boolean last;
}
