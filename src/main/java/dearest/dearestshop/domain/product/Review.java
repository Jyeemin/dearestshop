package dearest.dearestshop.domain.product;

import dearest.dearestshop.domain.BaseTimeEntity;
import dearest.dearestshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @OneToMany(mappedBy = "review",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    //연관관계 편의 메소드
    public void addReviewImage(ReviewImage reviewImage){
        images.add(reviewImage);
        reviewImage.addReview(this);
    }

    public void addReviewImages(List<ReviewImage> images){
        for (ReviewImage image : images) {
            addReviewImage(image);
        }
    }

    public void addProduct(Product product){
        this.product = product;
    }

    //생성 메소드
    public static Review createReview(
            String content,
            Member member,
            List<ReviewImage> images,
            Product product
    ){
        Review review = new Review();
        review.content = content;
        review.member = member;
        review.addReviewImages(images);
        product.addReview(review);
        return review;
    }

    //편의 메소드

}
