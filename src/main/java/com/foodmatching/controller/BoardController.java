package com.foodmatching.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.foodmatching.model.FileUploadForm;

@Controller("/foods")
public class BoardController {
	
	
	
	@GetMapping("/upload")
	public String uploadFile(Model model) {

		FileUploadForm fuf = new FileUploadForm();

		model.addAttribute("fileUpload", fuf);
		model.addAttribute("ok", "Yet");

		return "write_food";
	}

	@PostMapping("/upload")
	public String saveFile(@ModelAttribute(value = "fileUpload") FileUploadForm fuf, Model model) throws IllegalStateException, IOException {

		String filename = fuf.getFiles().get(0).getOriginalFilename();

		model.addAttribute("ok", filename);

		String sourceFileName = fuf.getFiles().get(0).getOriginalFilename();
		String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

		File destinationFile;
		String destinationFileName;
		do {
			destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
			destinationFile = new File("/Users/shin/test/" + destinationFileName);
		} while (destinationFile.exists());
		destinationFile.getParentFile().mkdirs();
		fuf.getFiles().get(0).transferTo(destinationFile);

		return "write_food";
	}

}
