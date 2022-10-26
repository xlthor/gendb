package de.amthor.gendb.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DatabaseResponse extends PageableResponseDto {

	@JsonView(Views.DatabaseResponse.class)
	@JsonProperty("databases")
	List<DatabaseDto> elements;
	
	@JsonView(Views.DatabaseResponse.class)
    int pageNo;
	
	@JsonView(Views.DatabaseResponse.class)
    int pageSize;
	
	@JsonView(Views.DatabaseResponse.class)
    long totalElements;
	
	@JsonView(Views.DatabaseResponse.class)
    int totalPages;
	
	@JsonView(Views.DatabaseResponse.class)
    private boolean last;
}
