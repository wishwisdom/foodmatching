package com.foodmatching.domain.model.user;

import com.foodmatching.domain.model.Scrap;
import com.foodmatching.domain.model.board.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString(exclude = "pocket")
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String email;
	private String nickname;
	private String password;
	private LocalDate birth;
	
	private LocalDateTime joinDay;

	@Embedded
	private Pocket pocket;

	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Set<Authority> roles;
	
	private String image;
	
	
	
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;

    public User(){
    	this.joinDay = LocalDateTime.now();
    	isAccountNonExpired = true;
    	isAccountNonLocked = true;
    	isCredentialsNonExpired = true;
    	isEnabled = true;
	}

	public Set<Authority> getRoles() {
		if( this.roles == null){
			roles = new HashSet<Authority>();
		}
		return roles;
	}


	public void addScrap(Scrap s){
		this.pocket.add(s);
	}

	public void removeScrap(Scrap s){
		this.pocket.remove(s);
	}
}
