/**
 * 
 */
package de.amthor.gendb.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author axel
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseResponse {

    private List<ReleaseDto> releases;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
