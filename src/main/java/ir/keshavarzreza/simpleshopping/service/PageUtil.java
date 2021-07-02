package ir.keshavarzreza.simpleshopping.service;

import ir.keshavarzreza.simpleshopping.domain.dto.ApiPage;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PageUtil {
	public ApiPage generateCorePage(Page page) {
		ApiPage apiPage = new ApiPage();
		BeanUtils.copyProperties(page, apiPage);
		apiPage.setTotalElements(page.getTotalElements());
		apiPage.setNumber(page.getNumber());
		apiPage.setNumberOfElements(page.getNumberOfElements());
		apiPage.setHasContent(page.hasContent());
		apiPage.setHasNext(page.hasNext());
		apiPage.setHasPrevious(page.hasPrevious());
		return apiPage;
	}
}
