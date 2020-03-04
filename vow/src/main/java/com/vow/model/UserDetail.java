package com.vow.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "UserDetails")
public class UserDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 10)
	private int userId;

	@NotNull
	@Size(max = 25)
	private String firstName;

	@NotNull
	@Size(max = 25)
	private String lastName;

	@NotNull
	@Size(max = 20)
	private String emailId;

	public UserDetail(Long id, @NotNull @Size(max = 10) int userId, @NotNull @Size(max = 25) String firstName,
			@NotNull @Size(max = 25) String lastName, @NotNull @Size(max = 25) String emailId) {
		super();
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
