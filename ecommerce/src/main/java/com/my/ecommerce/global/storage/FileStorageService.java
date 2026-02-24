package com.my.ecommerce.global.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

  private final String uploadDir = System.getProperty("user.dir") + "/uploads";

  public String save(MultipartFile file) throws IOException {

    if(file ==null || file.isEmpty()){
      throw new IllegalArgumentException("파일 없음");
    }

    validateImage(file);

    String originalName = file.getOriginalFilename();
    if(originalName == null || !originalName.contains(".")){
      throw new IllegalArgumentException("파일 이름이 없습니다.");
    }

    int doIndex = originalName.lastIndexOf(".");
    if(doIndex == -1){
      throw new IllegalArgumentException("확장자가 없습니다.");
    }

    String extension = originalName.substring(originalName.lastIndexOf(".")).toLowerCase();

    String fileName = UUID.randomUUID() + extension;

    Path uploadPath = Paths.get(uploadDir);
    Files.createDirectories(uploadPath);

    Path filePath = uploadPath.resolve(fileName);
    file.transferTo(filePath);

    return "/uploads/" + fileName;
  }

  private void validateImage(MultipartFile file){
    String contentType = file.getContentType();

    if(contentType == null || !contentType.startsWith("image")){
      throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
    }

    String originalName = file.getOriginalFilename();

    if(originalName == null || 
      !(originalName.toLowerCase().endsWith(".jpg") ||
        originalName.endsWith(".jpeg") ||
        originalName.endsWith(".png"))) {
        throw new IllegalArgumentException("허용 되지 않는 이미지 형식");
    }

    if(file.getSize() > 5*1024*1024){
      throw new IllegalArgumentException("파일 크기는 5MB 이하만 가능");
    }
  }

}
