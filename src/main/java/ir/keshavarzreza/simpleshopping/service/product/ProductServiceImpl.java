package ir.keshavarzreza.simpleshopping.service.product;

import ir.keshavarzreza.simpleshopping.domain.dto.ApiPage;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
import ir.keshavarzreza.simpleshopping.domain.dto.product.CreateProductRequest;
import ir.keshavarzreza.simpleshopping.domain.dto.product.ProductMaster;
import ir.keshavarzreza.simpleshopping.domain.dto.product.UpdateProductRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.Product;
import ir.keshavarzreza.simpleshopping.exceptions.*;
import ir.keshavarzreza.simpleshopping.repository.ProductRepository;
import ir.keshavarzreza.simpleshopping.service.PageUtil;
import ir.keshavarzreza.simpleshopping.service.PaginationService;
import ir.keshavarzreza.simpleshopping.service.category.CategoryService;
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
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final PaginationService paginationService;
	private final PageUtil pageUtil;
	private final CategoryService categoryService;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, PaginationService paginationService, PageUtil pageUtil, CategoryService categoryService) {
		this.productRepository = productRepository;
		this.paginationService = paginationService;
		this.pageUtil = pageUtil;
		this.categoryService = categoryService;
	}

	@Override
	public Product show(String id) throws ProductNotFoundException {
		if (!productRepository.existsById(id))
			throw new ProductNotFoundException();
		return productRepository.findById(id).get();
	}

	@Override
	public Product create(CreateProductRequest request) throws ProductAlreadyExistsException, CategoryNotFoundException {
		if (productRepository.existsByNameAndCategoryId(request.getName(), request.getCategoryId()))
			throw new ProductAlreadyExistsException();
		// verify if category_id belongs to an existing category
		if (!productRepository.existsById(request.getCategoryId()))
			throw new CategoryNotFoundException();
		Product productToPersist = new Product();
		BeanUtils.copyProperties(request, productToPersist);
		productToPersist.setCategory(categoryService.show(request.getCategoryId()));
		Product persistedProduct = productRepository.save(productToPersist);
		return persistedProduct;
	}

	@Override
	public boolean delete(String id) throws ProductAlreadyInUseException, ProductNotFoundException {
		if (!productRepository.existsById(id))
			throw new ProductNotFoundException();
		// TODO: 2021-07-01 throw category in use exception
		productRepository.deleteById(id);
		return true;
	}

	@Override
	public Product update(String id, UpdateProductRequest request) throws ProductAlreadyExistsException, ProductNotFoundException, CategoryNotFoundException {
		if (!productRepository.existsById(id))
			throw new ProductNotFoundException();
		if (productRepository.existsByNameAndCategoryIdAndIdNot(request.getName(), request.getCategoryId(), id))
			throw new ProductAlreadyExistsException();
		// verify if category_id belongs to an existing category
		if (!categoryService.exists(request.getCategoryId()))
			throw new CategoryNotFoundException();
		Product productToUpdate = new Product();
		BeanUtils.copyProperties(request, productToUpdate);
		productToUpdate.setCategory(categoryService.show(request.getCategoryId()));
		productToUpdate.setId(id);
		Product updatedProduct = productRepository.save(productToUpdate);
		return updatedProduct;
	}

	@Override
	public ApiPage<ProductMaster> findList(String name, String categoryId, ApiPagination pagination) throws SortParameterHasProblomException {
		Pageable pageable = paginationService.toPageable(pagination);
		boolean filterByName = (name == null || name.isEmpty()) ? false : true;
		boolean filterByCategoryId = (categoryId == null || categoryId.isEmpty()) ? false : true;
		Page<Product> all = null;

		if (!filterByName && !filterByCategoryId)
			all = productRepository.findAll(pageable);
		else if (filterByName && !filterByCategoryId)
			all = productRepository.findAllByNameContaining(name, pageable);
		else if (!filterByName && filterByCategoryId)
			all = productRepository.findAllByCategoryIdEquals(categoryId, pageable);
		else if (filterByName && filterByCategoryId)
			all = productRepository.findAllByNameContainingAndCategoryIdEquals(name, categoryId, pageable);

		List<ProductMaster> collect = all.stream()
				.map(category -> new ProductMaster(
						category.getId(),
						category.getName(),
						category.getCategory() != null ? category.getCategory().getId() : null))
				.collect(Collectors.toList());

		ApiPage<ProductMaster> apiPage = pageUtil.generateCorePage(all);
		apiPage.setContent(collect);
		return apiPage;
	}
}
