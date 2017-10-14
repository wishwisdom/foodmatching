package com.foodmatching.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;


public class FileUtil {
	private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private final static int RANDOM_NAME_LENGTH = 32;
	

	public static String extractDestinationFileName(MultipartFile m,String extendsion) {
		return RandomStringUtils.randomAlphanumeric(RANDOM_NAME_LENGTH) + "." + extendsion;
	}

	/*
	 * If files exist, save all. But if throws RuntimeException, will rollback
	 */
	public static void saveFileMap(String SAVE_PATH, Map<String, MultipartFile> fileMap) {

		int count = 0;
		Iterator<String> itr = fileMap.keySet().iterator();
		try {
			while (itr.hasNext()) {
				String key = itr.next();
				logger.info("key : {}",key);
				MultipartFile f = fileMap.get(key);
				saveFile(SAVE_PATH, key, f);
				count++;
			}
		} catch (IllegalStateException | IOException e) {
			if(count > 0){
				fileMap.forEach((k, f) -> {
					File file = new File(SAVE_PATH, k);
					if (file.exists()) {
						file.delete();
						logger.info("Because of RuntimeException, Deleted " + file.getAbsolutePath() + file.getName());
					}
				});
			}
			
			throw new RuntimeException("It's an IOexception that couldn't save files");
		}

	}

	public static void saveFile(final String SAVE_PATH, final String fileName, MultipartFile file)
			throws IllegalStateException, IOException {
		logger.info("Path, file : {} , {}",SAVE_PATH,fileName);
		File destinationFile = new File(SAVE_PATH, fileName);
		file.transferTo(destinationFile);
		logger.info("multipart size : " + destinationFile.getPath());

	}

	/**
	 * Return Image byte[]
	 * 
	 * @param SAVE_PATH
	 *            a path saving images
	 * @param fileName
	 * @return image byte[]
	 */
	public static byte[] getFile(String SAVE_PATH, String fileName) {
		try {
			File sourceimage = new File(SAVE_PATH, fileName);
			logger.info("FileName : " + fileName);
			logger.info("PATH:" + sourceimage.getAbsolutePath());
			BufferedImage image = ImageIO.read(sourceimage);
			// Retrieve image from the classpath.
			String extension = fileName.substring(fileName.indexOf('.') + 1);

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
