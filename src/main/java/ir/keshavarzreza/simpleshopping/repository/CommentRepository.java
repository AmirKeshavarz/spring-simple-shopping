package ir.keshavarzreza.simpleshopping.repository;

import ir.keshavarzreza.simpleshopping.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {

}
