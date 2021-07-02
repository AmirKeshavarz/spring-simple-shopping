package ir.keshavarzreza.simpleshopping.repository;

import ir.keshavarzreza.simpleshopping.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
	boolean existsByNameAndParentId(String name, String parentId);

	boolean existsByNameAndParentIdAndIdNot(String name, String parentId, String id);

	Page<Category> findAllByNameContaining(String name, Pageable pageable);

	Page<Category> findAllByNameContainingAndParentIdEquals(String name, String parentId, Pageable pageable);

	Page<Category> findAllByParentIdEquals(String parentId, Pageable pageable);
}
