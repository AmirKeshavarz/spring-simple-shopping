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
import ir.keshavarzreza.simpleshopping.repository.CategoryRepository;
import ir.keshavarzreza.simpleshopping.service.PageUtil;
import ir.keshavarzreza.simpleshopping.service.PaginationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;
	private final PaginationService paginationService;
	private final PageUtil pageUtil;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository, PaginationService paginationService, PageUtil pageUtil) {
		this.categoryRepository = categoryRepository;
		this.paginationService = paginationService;
		this.pageUtil = pageUtil;
	}

	@Override
	public Category show(String id) throws CategoryNotFoundException {
		if (!categoryRepository.existsById(id))
			throw new CategoryNotFoundException();
		return categoryRepository.findById(id).get();
	}

	@Override
	public Category create(CreateCategoryRequest request) throws CategoryAlreadyExistsException, CategoryNotFoundException {
		if (categoryRepository.existsByNameAndParentId(request.getName(), request.getParentId()))
			throw new CategoryAlreadyExistsException();
		Category categoryToPersist = new Category();
		BeanUtils.copyProperties(request, categoryToPersist);
		categoryToPersist.setParent(categoryRepository.findById(request.getParentId()).orElseThrow(() -> {
			return new CategoryNotFoundException();
		}));
		Category persistedCategory = categoryRepository.save(categoryToPersist);
		return persistedCategory;
	}

	@Override
	public boolean delete(String id) throws CategoryAlreadyInUseException, CategoryNotFoundException {
		if (!categoryRepository.existsById(id))
			throw new CategoryNotFoundException();
		// TODO: 2021-07-01 throw category in use exception
		categoryRepository.deleteById(id);
		return true;
	}

	@Override
	public Category update(String id, UpdateCategoryRequest request) throws CategoryAlreadyExistsException, CategoryNotFoundException {
		if (!categoryRepository.existsById(id))
			throw new CategoryNotFoundException();
		if (categoryRepository.existsByNameAndParentIdAndIdNot(request.getName(), request.getParentId(), id))
			throw new CategoryAlreadyExistsException();
		Category categoryToUpdate = new Category();
		BeanUtils.copyProperties(request, categoryToUpdate);
		categoryToUpdate.setParent(categoryRepository.findById(request.getParentId()).orElseThrow(() -> {
			return new CategoryNotFoundException();
		}));
		categoryToUpdate.setId(id);
		Category updatedCategory = categoryRepository.save(categoryToUpdate);
		return updatedCategory;
	}

	@Override
	public ApiPage<CategoryMaster> findList(String name, String parentId, ApiPagination pagination) throws SortParameterHasProblomException {
		Pageable pageable = paginationService.toPageable(pagination);
		boolean filterByName = (name == null || name.isEmpty()) ? false : true;
		boolean filterByParentId = (parentId == null || parentId.isEmpty()) ? false : true;
		Page<Category> all = null;

		if (!filterByName && !filterByParentId)
			all = categoryRepository.findAll(pageable);
		else if (filterByName && !filterByParentId)
			all = categoryRepository.findAllByNameContaining(name, pageable);
		else if (!filterByName && filterByParentId)
			all = categoryRepository.findAllByParentIdEquals(parentId, pageable);
		else if (filterByName && filterByParentId)
			all = categoryRepository.findAllByNameContainingAndParentIdEquals(name, parentId, pageable);

		List<CategoryMaster> collect = all.stream()
				.map(category -> new CategoryMaster(
						category.getId(),
						category.getName(),
						category.getParent() != null ? category.getParent().getId() : null))
				.collect(Collectors.toList());

		ApiPage<CategoryMaster> apiPage = pageUtil.generateCorePage(all);
		apiPage.setContent(collect);


		return apiPage;
	}

	@Override
	public boolean exists(String id) {
		return categoryRepository.existsById(id);
	}
}
