package dearest.dearestshop.domain.product;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class FileInfo {

    private String originalName;
    private String storedName;
    private Long fileSize;
    private String imgUrl;

    //생성 메소드
    public static FileInfo createFileInfo(
            String originalName,
            String storedName,
            Long fileSize,
            String imgUrl
    ){
        FileInfo fileInfo = new FileInfo();
        fileInfo.originalName = originalName;
        fileInfo.storedName = storedName;
        fileInfo.fileSize = fileSize;
        fileInfo.imgUrl = imgUrl;
        return fileInfo;
    }

}
