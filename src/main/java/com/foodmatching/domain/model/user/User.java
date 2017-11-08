package com.foodmatching.domain.model.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Email(message="must be email form!")
	private String email;
	
	
	private String nickname;

	@NotEmpty
	@Size(min = 6, message = "must be at least 6 characters")
	private String password;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	
	private LocalDateTime joinDay;


	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private Set<Authority> roles;
	
	private String image;
	
	
	
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;

    public User(){
    	this.joinDay = LocalDateTime.now();
	}

	public Set<Authority> getRoles() {
		if( this.roles == null){
			roles = new HashSet<Authority>();
		}
		return roles;
	}


}
