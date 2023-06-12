package com.hnu.capstone.domain;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileStore {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;


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
        //multipartFile.transferTo(new File(getFullPath(storeFileName)));
        uploadToS3(storeFileName, multipartFile);
        System.out.println(amazonS3Client.getUrl("frientopia", storeFileName));


        return new UploadFile(originalFileName, storeFileName); //store~ : 경로+UUID화된파일이름
    }

    private void uploadToS3(String fileName, MultipartFile multipartFile) throws IOException {
        String filePath = "post_upload/" + fileName;
        //File file = new File(getFullPath(fileName));

        File file = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        amazonS3Client.putObject(new PutObjectRequest(bucketName, filePath, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        // 로컬에 저장된 파일 삭제
        if (file.delete()) {
            System.out.println("File delete success");
        } else {
            System.out.println("File delete fail");
        }
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

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

}


