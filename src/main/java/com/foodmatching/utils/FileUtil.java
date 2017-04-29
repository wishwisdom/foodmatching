package com.foodmatching.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.foodmatching.serviceimpl.BoardServiceImpl;

public class FileUtil {
	private final static Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	private final static String SAVE_PATH= "C:\\Users\\calina\\git\\foodmatching\\src\\main\\resources\\static\\img";
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
	public static void saveFileMap(Map<String,MultipartFile> fileMap){
		
		fileMap.forEach((key,file)->{
			File destinationFile = new File(SAVE_PATH, key);
			try {
				file.transferTo(destinationFile);
				logger.info("multipart size : "+destinationFile.getPath());
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("It's an IOexception that couldn't save files");
			}
		});
	}
	
	public static byte[] getFile(String fileName){
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
