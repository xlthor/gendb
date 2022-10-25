package de.amthor.gendb.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseResponse {

	@JsonView(Views.DatabaseResponse.class)
	private List<DatabaseDto> databases;
	
	@JsonView(Views.DatabaseResponse.class)
    private int pageNo;
	
	@JsonView(Views.DatabaseResponse.class)
    private int pageSize;
	
	@JsonView(Views.DatabaseResponse.class)
    private long totalElements;
	
	@JsonView(Views.DatabaseResponse.class)
    private int totalPages;
	
	@JsonView(Views.DatabaseResponse.class)
    private boolean last;
}
