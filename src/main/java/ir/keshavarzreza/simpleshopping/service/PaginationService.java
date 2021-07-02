package ir.keshavarzreza.simpleshopping.service;

import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
import ir.keshavarzreza.simpleshopping.exceptions.SortParameterHasProblomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class PaginationService {
	public Pageable toPageable(ApiPagination apiPagination) throws InvalidPropertyException, SortParameterHasProblomException {

		Pageable pageable;

		if (StringUtils.isEmpty(apiPagination.getInputSortString())) {
			// if sort not needed
			pageable = PageRequest.of(apiPagination.getPage(), apiPagination.getSize());
			log.trace("Pagination created with no sort!");
			return pageable;
		}

		Sort jpaSort = EntitySortBuilder.buildSort(apiPagination.getInputSortString());

		pageable = PageRequest.of(apiPagination.getPage(), apiPagination.getSize(), jpaSort);
		log.trace("Pagination created with sort!");

		return pageable;
	}

}