package dearest.dearestshop.domain.product;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ProductImage {

    @Id @GeneratedValue
    @Column(name = "product_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    private int sortOrder;

    @Embedded
    private FileInfo fileInfo;

    public ProductImage(Long id,
                        Product product,
                        ImageType imageType,
                        int sortOrder,
                        FileInfo fileInfo) {
        this.id = id;
        this.product = product;
        this.imageType = imageType;
        this.sortOrder = sortOrder;
        this.fileInfo = fileInfo;
    }
}
