package dearest.dearestshop.domain.product;

import dearest.dearestshop.domain.BaseTimeEntity;
import dearest.dearestshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @OneToMany(mappedBy = "review")
    private List<ReviewImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    public Review(Long id,
                  String content,
                  Member member,
                  List<ReviewImage> images,
                  Product product) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.images = images;
        this.product = product;
    }
}
