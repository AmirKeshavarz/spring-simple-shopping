package ir.keshavarzreza.simpleshopping.service.product;

import ir.keshavarzreza.simpleshopping.domain.dto.ApiPage;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
import ir.keshavarzreza.simpleshopping.domain.dto.product.CreateProductRequest;
import ir.keshavarzreza.simpleshopping.domain.dto.product.ProductMaster;
import ir.keshavarzreza.simpleshopping.domain.dto.product.UpdateProductRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.Product;
import ir.keshavarzreza.simpleshopping.exceptions.CategoryNotFoundException;
import ir.keshavarzreza.simpleshopping.exceptions.ProductAlreadyExistsException;
import ir.keshavarzreza.simpleshopping.exceptions.ProductNotFoundException;
import ir.keshavarzreza.simpleshopping.exceptions.SortParameterHasProblomException;
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

import java.math.BigDecimal;
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
		return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
	}

	@Override
	public Product create(CreateProductRequest request) throws ProductAlreadyExistsException, CategoryNotFoundException {
		if (productRepository.existsByNameAndCategoryId(request.getName(), request.getCategoryId()))
			throw new ProductAlreadyExistsException();
		// verify if category_id belongs to an existing category
		if (!categoryService.exists(request.getCategoryId()))
			throw new CategoryNotFoundException();
		Product productToPersist = new Product();
		BeanUtils.copyProperties(request, productToPersist);
		productToPersist.setCategory(categoryService.show(request.getCategoryId()));
		return  productRepository.save(productToPersist);
	}

	@Override
	public boolean delete(String id) throws ProductNotFoundException {
		if (!productRepository.existsById(id))
			throw new ProductNotFoundException();
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
		if (request.getCategoryId() != null && !categoryService.exists(request.getCategoryId()))
			throw new CategoryNotFoundException();
		Product productToUpdate = new Product();
		BeanUtils.copyProperties(request, productToUpdate);
		if (request.getCategoryId() != null)
			productToUpdate.setCategory(categoryService.show(request.getCategoryId()));
		productToUpdate.setId(id);
		return productRepository.save(productToUpdate);
	}

	@Override
	public ApiPage<ProductMaster> findList(String name, String categoryId, BigDecimal minPrice, BigDecimal maxPrice, ApiPagination pagination) throws SortParameterHasProblomException {
		Pageable pageable = paginationService.toPageable(pagination);
		boolean filterByName = name != null && !name.isEmpty();
		boolean filterByCategoryId = categoryId != null && !categoryId.isEmpty();
		boolean filterByPrice = minPrice != null || maxPrice != null;
		if (filterByPrice) {
			if (minPrice == null) minPrice = BigDecimal.valueOf(0);
			if (maxPrice == null) maxPrice = BigDecimal.valueOf(Double.MAX_VALUE);
		}
		Page<Product> all = null;

		if (!filterByName && !filterByCategoryId && !filterByPrice)
			all = productRepository.findAll(pageable);
		else if (filterByName && !filterByCategoryId && !filterByPrice)
			all = productRepository.findAllByNameContaining(name, pageable);
		else if (!filterByName && filterByCategoryId && !filterByPrice)
			all = productRepository.findAllByCategoryIdEquals(categoryId, pageable);
		else if (filterByName && filterByCategoryId && !filterByPrice)
			all = productRepository.findAllByNameContainingAndCategoryIdEquals(name, categoryId, pageable);
		else if (!filterByName && !filterByCategoryId && filterByPrice)
			all = productRepository.findAllByPriceBetween(minPrice,maxPrice,pageable);
		else if (filterByName && !filterByCategoryId && filterByPrice)
			all = productRepository.findAllByNameContainingAndPriceBetween(name, minPrice, maxPrice, pageable);
		else if (!filterByName && filterByCategoryId && filterByPrice)
			all = productRepository.findAllByCategoryIdEqualsAndPriceBetween(categoryId, minPrice, maxPrice, pageable);
		else if (filterByName && filterByCategoryId && filterByPrice)
			all = productRepository.findAllByNameContainingAndCategoryIdEqualsAndPriceBetween(name, categoryId, minPrice, maxPrice, pageable);


		List<ProductMaster> collect = all.stream()
				.map(p -> new ProductMaster(
						p.getId(),
						p.getName(),
						p.getCategory() != null ? p.getCategory().getId() : null,
						p.getPrice())
				)
				.collect(Collectors.toList());

		ApiPage<ProductMaster> apiPage = pageUtil.generateCorePage(all);
		apiPage.setContent(collect);
		return apiPage;
	}
}
