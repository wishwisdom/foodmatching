package com.foodmatching.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.foodmatching.serviceimpl.BoardServiceImpl;

public class FileUtil {
	private final static Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	private final static int RANDOM_NAME_LENGTH = 32;
	
	public static String extractDestinationFileName(MultipartFile m){
		String sourceFileName = m.getOriginalFilename();
		String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
		
		return RandomStringUtils.randomAlphanumeric(RANDOM_NAME_LENGTH) + "." + sourceFileNameExtension;
	}
	
	/*
	 * If files exist, save all.
	 * But if throws RuntimeException, will rollback
	 */
	public static void saveFileMap(final String SAVE_PATH,Map<String,MultipartFile> fileMap){
		
		fileMap.forEach((key,file)->{
			saveFile(SAVE_PATH,key,file);
		});
	}
	
	public static void saveFile(final String SAVE_PATH,String fileName, MultipartFile file){
		File destinationFile = new File(SAVE_PATH, fileName);
		try {
			file.transferTo(destinationFile);
			logger.info("multipart size : "+destinationFile.getPath());
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("It's an IOexception that couldn't save files");
		}
	}
	
	/**
	 * Return Image byte[]
	 * 
	 * @param SAVE_PATH a path saving images
	 * @param fileName  
	 * @return image byte[]
	 */
	public static byte[] getFile(final String SAVE_PATH,String fileName){
		 try {
			 	File sourceimage = new File(SAVE_PATH,fileName);
			 	logger.info("FileName : "+fileName);
			 	logger.info("PATH:"+sourceimage.getAbsolutePath());
			 	BufferedImage image = ImageIO.read(sourceimage);
		        // Retrieve image from the classpath.
		        String extension = fileName.substring(fileName.indexOf('.')+1);
		        

		        // Create a byte array output stream.
		        ByteArrayOutputStream bao = new ByteArrayOutputStream();

		        // Write to output stream
		        ImageIO.write(image, extension, bao);

		        return bao.toByteArray();
		    } catch (IOException e) {
		        logger.error(e.getMessage());
		        throw new RuntimeException(e);
		    }
	}
}
