package com.foodmatching.domain.model;

import com.foodmatching.domain.model.board.Board;
import com.foodmatching.domain.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Scrap implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long  id;

	@ManyToOne(optional = false)
	private User user;

	@ManyToOne(optional = false)
	private Board board;

	private LocalDateTime registeredAt;

	public Scrap(User user,Board board){
		this.user = user;
		this.board = board;
		this.registeredAt = LocalDateTime.now();
	}


}