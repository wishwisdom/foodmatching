package com.foodmatching.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.foodmatching.model.Board;
import com.foodmatching.model.CustomUser;
import com.foodmatching.model.FileUploadForm;
import com.foodmatching.serviceimpl.BoardServiceImp;

@Controller("/foods")
public class BoardController {
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardServiceImp boardService;
	

	@PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('ROLE_USER')")
	@GetMapping("/upload")
	public String uploadFile(Model model) {

		FileUploadForm fuf = new FileUploadForm();

		model.addAttribute("fileUpload", fuf);
		model.addAttribute("ok", "Yet");

		return "write_food";
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('ROLE_USER')")
	@PostMapping("/upload")
	public String saveFile(@ModelAttribute(value = "fileUpload") FileUploadForm fuf, @ModelAttribute("customUser") CustomUser user, Model model)
			throws IllegalStateException, IOException {

		String filename = fuf.getFiles().get(0).getOriginalFilename();

		model.addAttribute("ok", filename);

		logger.info("Files : " + fuf.getFiles().size());

//		private String writer;
//		
//		private Long good;
//		private String content;
//		
		Board b = new Board();
		
		b.setNickname(user.getUsername());
		b.setTitle("test");
		
		boardService.save(b);
//		fuf.getFiles().forEach((m) -> {
//			logger.info("File Name :" + m.getOriginalFilename());
//			String sourceFileName = m.getOriginalFilename();
//			String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
//
//			String destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
//			
//			// For saving files, need to be the absolute path to save them.
//			
//			File destinationFile = new File( "/Users/shin/Documents/workspace-sts-3.8.2.RELEASE/foodmatching",destinationFileName);
//			try {
//				m.transferTo(destinationFile);
//				logger.info("multipart size : "+m.getSize());
//			} catch (IllegalStateException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		});

		return "write_food";
	}

}
