package com.hotelbooking.mobileapp.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads/";

    public String saveFile(MultipartFile file) throws Exception {

        if(file == null || file.isEmpty()){
            return null;
        }

        File folder = new File(uploadDir);
        if(!folder.exists()){
            folder.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);

        file.transferTo(dest);

        return uploadDir + fileName;
    }
}