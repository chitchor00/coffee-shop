package org.swaroop.coffeestore.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Auditable {
	@CreatedDate
	@Column(name = "d_created_date", nullable = false, updatable = false)
	private Instant createdDate;

	@CreatedBy
	@Column(name = "f_created_by_user", nullable = false, updatable = false)
	private String createdByUser;

	@LastModifiedDate
	@Column(name = "d_last_modified_date", insertable = false)
	private Instant lastModifiedDate;

	@LastModifiedBy
	@Column(name = "f_last_modified_by_user_id", nullable = false, updatable = false)
	private String lastModifiedByUser;

	// TODO: @Version for optimistic locking
}