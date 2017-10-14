package com.foodmatching.domain.model;

import com.foodmatching.domain.model.board.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter @Setter
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Board board;
	private String foodName;
	private String foodImage;
	private String foodSavedLocation;
	private String location;
}
