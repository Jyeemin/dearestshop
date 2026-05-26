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

}
