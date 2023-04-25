package com.toy.blog.api.service.file;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FileServiceUtil {

    private final S3ServiceUtil s3ServiceUtil;

    @Value("${cloud.aws.url}")
    private String url;

    /**
     * 0. [넘어온 파일이 지정된 형식의 이미지 파일인지 확인]
     * */
    public boolean isAllImageExt(List<MultipartFile> imageList){
        return imageList.stream()
                .map(MultipartFile::getOriginalFilename)
                .map(FilenameUtils::getExtension)
                .allMatch(e -> e.equals("jpeg") || e.equals("jpg") || e.equals("png") || e.equals("JPG") || e.equals("JPEG") || e.equals("PNG"));
    }

    /**
     * 1. [file Original Name을 가지고 -> 실제 파일이 저장될 path들의 리스트를 생성하는 서비스]
     * */
    public List<String> getPathList(List<MultipartFile> imageList, Long articleId){

        //1. 오늘 날짜에 대한 url 앞부분을 만들기
        LocalDate now = LocalDate.now();
        String today = now.getYear() + "_" + now.getMonth().getValue() + "_" + now.getDayOfMonth();

        //2. reviewId를 가지고 중간부분 만들기 (memberId를 노출시키는건 위험하다고 판단하여 노출시키지 않음)
        String articleNumber = "article" + articleId;

        //3. today와 articleNumber 그리고 UUID 값 + 마지막으로 확장자를 더하여 -> 각 이미지가 저장될 원본 path를 만듦

        return imageList.stream()
                .map(i -> "blog" + File.separator + today + File.separator + articleNumber + File.separator + UUID.randomUUID().toString().substring(0, 7) +
                        "." + FilenameUtils.getExtension(i.getOriginalFilename()))
                .collect(Collectors.toList());
    }


    /**
     * 2. [인자로 넘어온 path에 , 이미지 파일을 실제로 저장하는 서비스]
     * */
    public void uploadFile(String path, MultipartFile image){
        s3ServiceUtil.uploadFile(path, image);
    }


    /**
     * 3. [path를 받아 , 각 이미지의 url을 만들어 리턴하는 서비스]
     * */
    public List<String> getImageUrlList(List<String> pathList){
        return pathList.stream()
                .map(p -> url + File.separator + p)
                .collect(Collectors.toList());
    }
}
