package com.hnu.capstone.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    public static String fileDir = System.getProperty("user.dir") + "/src/main/resources/static/post_upload/";

    /* 전체 파일 경로 */
    public String getFullPath(String fileName){
        return fileDir + fileName;
    }
    /* 파일 저장 */
    //다중 파일인 경우
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {

        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }
    //한개인 경우
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException{
        if(multipartFile.isEmpty())
        {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();

        /*　서버에 저장할 새로운 파일명 생성 */
        String storeFileName = createStoreFileName(originalFileName);

        /* 새 파일명으로 파일 저장 */
        multipartFile.transferTo(new File(getFullPath(storeFileName)));


        return new UploadFile(originalFileName, storeFileName);
    }
    /* 확장자명 추출 메서드 */
    private String extractExtension(String originalFileName){
        int position = originalFileName.lastIndexOf(".");//확장자명 위치
        String extension = originalFileName.substring(position + 1); //확장자명

        return extension;
    }

    /* 서버에 저장할 파일명 생성 */
    private String createStoreFileName(String originalFileName){
        //서버에 저장하는 파일명
        String uuid = UUID.randomUUID().toString();

        //확장자를 붙여서 최종적으로 저장할 파일명 정의
        String extention = extractExtension(originalFileName);
        String storeFileName = uuid + "." + extention;

        return storeFileName;
    }

}

