package com.foodmatching.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by shin on 2017. 10. 31..
 */
@Component
@Slf4j
@NoArgsConstructor
public class AWSUploader {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");


    @Value("${cloud.aws.access-key}")
    String ACCESS_KEY;
    @Value("${cloud.aws.secret-key}")
    String SECRET_KEY;
    @Value("${cloud.aws.bucket-name}")
    String BUCKET_NAME;


    private final String ENDPOINT = "https://s3-ap-northeast-2.amazonaws.com";



    public String upload(MultipartFile multipartFile, String path){

        String extention = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.')+1);
        String fileName = LocalDateTime.now().format(FORMATTER).toString()+"."+extention;
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);

        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));




        String url =ENDPOINT+"/footmatch/"+path+"/"+datePath+"/";

        if (amazonS3 != null) {
            try {
//                File file = new File("/Users/shin/Documents/workspace-sts-3.8.2.RELEASE/foodmatching/" + fileName);
//                multipartFile.transferTo(file);

                //log.info("file path : {}", file.getAbsolutePath());
                ObjectMetadata om = new ObjectMetadata();

                Long contentLength = Long.valueOf(IOUtils.toByteArray(multipartFile.getInputStream()).length);
                om.setContentLength(contentLength);

                PutObjectRequest putObjectRequest =
                        new PutObjectRequest(BUCKET_NAME+"/"+path+"/"+datePath , fileName, multipartFile.getInputStream(), om);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // file permission

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
                amazonS3.putObject(putObjectRequest); // upload file

                url+=fileName;

            } catch (AmazonServiceException ase) {
                ase.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                amazonS3 = null;
            }
        }
        return url;
    }




}
