package de.amthor.gendb.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ColumnResponse extends PageableResponseDto {

	@JsonView({Views.Response.class})
	@JsonProperty("columns")
    List<ColumnsDto> elements;
	
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
