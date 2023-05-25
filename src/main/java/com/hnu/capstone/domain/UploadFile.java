package com.hnu.capstone.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadFile {
    private String uploadFileName; //유저가 업로드한 파일명
    private String storeFileName; //서버가 관리하는 파일명

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
