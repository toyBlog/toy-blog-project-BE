package com.toy.blog.api.service.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.toy.blog.api.exception.file.FailSaveFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class S3ServiceUtil {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 1. [인자로 넘어온 file을 , 인자로 넘어온 path에 저장하는 서비스]
     */
    public void uploadFile(String path, MultipartFile file) {
        try {
            //1. meta 데이터를 metadata객체 생성 후 데이터 넣기
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            //2. 이제 실제 그 path에 , 실제 이미지와 , 메타데이터를 함께 저장
            amazonS3Client.putObject(bucket, path, file.getInputStream(), metadata);

        } catch (IOException e) {
            throw new FailSaveFileException();
        }
    }
}
