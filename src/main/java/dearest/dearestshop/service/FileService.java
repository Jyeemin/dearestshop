package dearest.dearestshop.service;

import dearest.dearestshop.domain.product.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FileService{
    @Value("${file.fileDir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir+filename;
    }


    private String ExtractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

    public FileInfo save(MultipartFile multipartFile){
        //있는지확인
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new RuntimeException("파일이 없습니다.");
        }

        String originalFilename = multipartFile.getOriginalFilename();
        //서버에 저장하는 파일명
        String uuid = UUID.randomUUID().toString();
        String ext = ExtractExt(originalFilename);
        String storeFileName = uuid + "." + ext;

        //imgUrl생성
        String imgUrl = "/images/" + storeFileName;

        //저장하고업로드파일생성
        String fullPath = getFullPath(storeFileName);
        try {
            multipartFile.transferTo(new File(fullPath));
        } catch (IOException e) {
            System.out.println("e = " + e);
            System.out.println("파일저장실패");
        }


        return FileInfo.createFileInfo(originalFilename, storeFileName, multipartFile.getSize(),imgUrl);
    }

    public List<FileInfo> saveFiles(List<MultipartFile> multipartFiles) {
        List<FileInfo> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile == null || multipartFile.isEmpty()) {
                throw new RuntimeException("파일이 없습니다.");
            }
                uploadFiles.add(save(multipartFile));
        }
        return uploadFiles;
    }



}
