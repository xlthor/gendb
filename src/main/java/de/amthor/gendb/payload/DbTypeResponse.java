package de.amthor.gendb.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonView(Views.Response.class)
public class DbTypeResponse {

	@JsonView(Views.Response.class)
	private List<DbTypeDto> dbtypes;
	
	@JsonView(Views.Response.class)
    private int pageNo;
	
	@JsonView(Views.Response.class)
    private int pageSize;
	
	@JsonView(Views.Response.class)
    private long totalElements;
	
	@JsonView(Views.Response.class)
    private int totalPages;
	
	@JsonView(Views.Response.class)
    private boolean last;
    
}