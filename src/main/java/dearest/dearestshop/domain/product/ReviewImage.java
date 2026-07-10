package dearest.dearestshop.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {

    @Id @GeneratedValue
    @Column(name = "review_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Embedded
    private FileInfo fileInfo;

    //생성 메소드
    public static ReviewImage createReviewImage(
            FileInfo fileInfo
    ){
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.fileInfo = fileInfo;
        return reviewImage;
    }
    
    //편의 메소드
    public void addReview(Review review){
        this.review = review;
    }
}
