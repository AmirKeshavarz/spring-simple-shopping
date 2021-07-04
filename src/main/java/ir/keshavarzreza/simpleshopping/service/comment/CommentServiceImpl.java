package ir.keshavarzreza.simpleshopping.service.comment;

import ir.keshavarzreza.simpleshopping.domain.dto.comment.CreateCommentRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.Comment;
import ir.keshavarzreza.simpleshopping.domain.entity.Product;
import ir.keshavarzreza.simpleshopping.exceptions.CategoryNotFoundException;
import ir.keshavarzreza.simpleshopping.exceptions.CommentNotFoundException;
import ir.keshavarzreza.simpleshopping.exceptions.ProductNotFoundException;
import ir.keshavarzreza.simpleshopping.repository.CommentRepository;
import ir.keshavarzreza.simpleshopping.service.product.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
	private CommentRepository commentRepository;
	private ProductService productService;

	public CommentServiceImpl(CommentRepository commentRepository, ProductService productService) {
		this.commentRepository = commentRepository;
		this.productService = productService;
	}

	@Override
	public void delete(String id) throws CommentNotFoundException {
		if (!commentRepository.existsById(id))
			throw new CommentNotFoundException();
		commentRepository.deleteById(id);
	}

	@Override
	public Comment create(String username, CreateCommentRequest request) throws ProductNotFoundException {
		if (!productService.existsById(request.getProductId()))
			throw new ProductNotFoundException();
		Comment commentToPersist = new Comment();
		BeanUtils.copyProperties(request, commentToPersist);
		commentToPersist.setUsername(username);
		return  commentRepository.save(commentToPersist);
	}


}
