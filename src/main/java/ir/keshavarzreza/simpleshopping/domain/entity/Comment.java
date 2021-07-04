package ir.keshavarzreza.simpleshopping.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class Comment extends BaseDomainEntity {

	@Column(length = 1000)
	private String comment;
	private String username;
	private boolean accepted = true;

	public Comment() {
	}

	public Comment(String comment, String username, boolean accepted) {
		this.comment = comment;
		this.username = username;
		this.accepted = accepted;
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
