package com.excilys.cdb.core.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = -7784560651717187287L;

	@Transient
	private String token = "";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 2, max = 255, message = "Should be between 2 and 255 character!")
	@NotNull
	@Basic(optional = false)
	@Column(name = "username")
	private String username;

	@Id
	@Size(min = 8, max = 255, message = "Should be between 8 and 255 character!")
	@NotNull
	@Basic(optional = false)
	@Column(name = "password")
	@JsonIgnore
	private String password;

	@JsonIgnore
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}
	
	@CreationTimestamp
	private Timestamp createdAt;

	@UpdateTimestamp
	private Timestamp updatedAt;

	@AssertTrue
	private boolean enabled;
	private boolean tokenExpired;

	private List<Role> roles;
	
	public User() {
		
	}

	public User(@Size(min = 2, max = 255, message = "Should be between 2 and 255 character!") @NotNull String username,
			@Size(min = 8, max = 255, message = "Should be between 8 and 255 character!") @NotNull String password,
			String email, boolean enabled, boolean tokenExpired, List<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.createdAt = Timestamp.from(Instant.now());
		this.updatedAt = Timestamp.from(Instant.now());
		this.enabled = enabled;
		this.tokenExpired = tokenExpired;
		this.roles = roles;
	}
	
	public User(Long id, String username, String password,String email,List<Role> roles) {
		super();
		this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		Optional<Role> admin = roles.stream()
			.filter(role -> role.getName().equals("ADMIN"))
			.findAny();
		if (admin.isPresent()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public static class UserBuilder {

		@Transient
		private String token = "";

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Size(min = 2, max = 255, message = "Should be between 2 and 255 character!")
		@NotNull
		@Basic(optional = false)
		@Column(name = "username")
		private String username;

		@Id
		@Size(min = 8, max = 255, message = "Should be between 8 and 255 character!")
		@NotNull
		@Basic(optional = false)
		@Column(name = "password")
		@JsonIgnore
		private String password;

		@CreationTimestamp
		private Timestamp createdAt;

		@UpdateTimestamp
		private Timestamp updatedAt;

		@JsonIgnore
		private String email;

		public String getEmail() {
			return email;
		}

		public UserBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		@AssertTrue
		private boolean enabled;
		private boolean tokenExpired;

		private List<Role> roles;

		public String getToken() {
			return token;
		}

		public UserBuilder setToken(String token) {
			this.token = token;
			return this;
		}

		public Long getId() {
			return id;
		}

		public UserBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public String getUsername() {
			return username;
		}

		public UserBuilder setUsername(String username) {
			this.username = username;
			return this;
		}

		public String getPassword() {
			return password;
		}

		public UserBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public Timestamp getCreatedAt() {
			return createdAt;
		}

		public UserBuilder setCreatedAt(Timestamp createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Timestamp getUpdatedAt() {
			return updatedAt;
		}

		public UserBuilder setUpdatedAt(Timestamp updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public UserBuilder setEnabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public boolean isTokenExpired() {
			return tokenExpired;
		}

		public UserBuilder setTokenExpired(boolean tokenExpired) {
			this.tokenExpired = tokenExpired;
			return this;
		}

		public List<Role> getRoles() {
			return roles;
		}

		public UserBuilder setRoles(List<Role> roles) {
			this.roles = roles;
			return this;
		}

		public User build() {
			return new User(username, password, email, true, false, roles);
		}
	}
}
