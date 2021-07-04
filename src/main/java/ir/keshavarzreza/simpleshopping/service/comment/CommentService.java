package ir.keshavarzreza.simpleshopping.service.comment;

import ir.keshavarzreza.simpleshopping.domain.dto.comment.CreateCommentRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.Comment;
import ir.keshavarzreza.simpleshopping.exceptions.CommentNotFoundException;
import ir.keshavarzreza.simpleshopping.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

	void delete(String id) throws CommentNotFoundException;

	Comment create(String username, CreateCommentRequest createCommentRequest) throws ProductNotFoundException;
}
