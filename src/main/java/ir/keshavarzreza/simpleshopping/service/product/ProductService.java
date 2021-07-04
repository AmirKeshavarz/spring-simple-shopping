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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ProductService {
	Product show(String id) throws ProductNotFoundException;

	Product create(CreateProductRequest request) throws ProductAlreadyExistsException, CategoryNotFoundException;

	boolean delete(String id) throws ProductNotFoundException;

	Product update(String id, UpdateProductRequest updateProductRequest) throws ProductAlreadyExistsException, ProductNotFoundException, CategoryNotFoundException;

	ApiPage<ProductMaster> findList(String name, String categoryId, BigDecimal minPrice, BigDecimal maxPrice, ApiPagination pagination) throws SortParameterHasProblomException;
}
