package ir.keshavarzreza.simpleshopping.domain.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class CreateCommentRequest {
	@NotBlank
	@Size(min = 1 , max = 500)
	private String comment;
	@NotBlank
	@NotNull
	private String productId;

	public CreateCommentRequest() {
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}
