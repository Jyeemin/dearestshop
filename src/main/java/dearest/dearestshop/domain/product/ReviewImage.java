package dearest.dearestshop.domain.product;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ReviewImage {

    @Id @GeneratedValue
    @Column(name = "review_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Embedded
    private FileInfo fileInfo;

    public ReviewImage(Long id, Review review, FileInfo fileInfo) {
        this.id = id;
        this.review = review;
        this.fileInfo = fileInfo;
    }
}
