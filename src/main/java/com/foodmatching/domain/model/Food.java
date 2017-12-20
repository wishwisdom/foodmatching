package com.foodmatching.domain.model;

import com.foodmatching.domain.model.board.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter @Setter
@Entity
public class Food {
	@Value("${cloud.aws.domain}")
	@Transient
	private static String domain;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Board board;
	private String foodName;
	private String foodImage;
	private String location;

	private LocalDateTime registeredAt;

	@OneToMany
	private List<FoodTaste> tastes;

	public String getFoodImage(){
		return domain+foodImage;
	}
}
