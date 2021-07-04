package ir.keshavarzreza.simpleshopping.domain.dto.comment;

import java.math.BigDecimal;

public class CommentDetail {
	private String id;
	private String comment;
	private String username;
	private boolean accepted;

	public CommentDetail() {
	}

	public CommentDetail(String id, String comment, String username, boolean accepted) {
		this.id = id;
		this.comment = comment;
		this.username = username;
		this.accepted = accepted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
}
