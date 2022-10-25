package de.amthor.gendb.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class GenericPageableResponse {

	/**
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	public static Pageable createPageable(int pageNo, int pageSize, String sortBy, String sortDir) {
		Pageable pageable;
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
		// create Pageable instance
        pageable = PageRequest.of(pageNo, pageSize, sort);
		return pageable;
	}
}
