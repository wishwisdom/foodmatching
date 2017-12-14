package com.foodmatching.domain.model.board;

import java.util.List;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {
	private List<MultipartFile> foodPictures;
	private List<String> foodNames;
	private List<String> foodTastes;
	private String summary;
}
