package ir.keshavarzreza.simpleshopping.service.category;

import ir.keshavarzreza.simpleshopping.domain.dto.ApiPage;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
import ir.keshavarzreza.simpleshopping.domain.dto.category.CategoryMaster;
import ir.keshavarzreza.simpleshopping.domain.dto.category.CreateCategoryRequest;
import ir.keshavarzreza.simpleshopping.domain.dto.category.UpdateCategoryRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.Category;
import ir.keshavarzreza.simpleshopping.exceptions.CategoryAlreadyExistsException;
import ir.keshavarzreza.simpleshopping.exceptions.CategoryAlreadyInUseException;
import ir.keshavarzreza.simpleshopping.exceptions.CategoryNotFoundException;
import ir.keshavarzreza.simpleshopping.exceptions.SortParameterHasProblomException;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
	Category show(String id) throws CategoryNotFoundException;

	Category create(CreateCategoryRequest request) throws CategoryAlreadyExistsException, CategoryNotFoundException;

	boolean delete(String id) throws CategoryAlreadyInUseException, CategoryNotFoundException;

	Category update(String id, UpdateCategoryRequest updateCategoryRequest) throws CategoryAlreadyExistsException, CategoryNotFoundException;

	ApiPage<CategoryMaster> findList(String name, String parentId, ApiPagination pagination) throws SortParameterHasProblomException;

	boolean exists(String id);
}
