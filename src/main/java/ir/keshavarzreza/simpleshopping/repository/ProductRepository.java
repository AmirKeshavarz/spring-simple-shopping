package ir.keshavarzreza.simpleshopping.repository;

import ir.keshavarzreza.simpleshopping.domain.entity.Category;
import ir.keshavarzreza.simpleshopping.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	boolean existsByCategoryId(String categoryId);
	boolean existsByNameAndCategoryId(String name, String categoryId);

	boolean existsByNameAndCategoryIdAndIdNot(String name, String categoryId, String id);

	Page<Product> findAllByNameContaining(String name, Pageable pageable);

	Page<Product> findAllByNameContainingAndCategoryIdEquals(String name, String categoryId, Pageable pageable);

	Page<Product> findAllByCategoryIdEquals(String categoryId, Pageable pageable);
}
