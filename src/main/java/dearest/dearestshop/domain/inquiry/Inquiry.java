package dearest.dearestshop.domain.inquiry;

import dearest.dearestshop.domain.BaseTimeEntity;
import dearest.dearestshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "INQUIRY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "inquiry", fetch = FetchType.LAZY)
    private InquiryAnswer inquiryAnswer;

    private String title;

    @Column(length = 3000)
    private String content;

    //연관관계 편의 메서드
    public void addInquiryAnswer(InquiryAnswer inquiryAnswer){
        this.inquiryAnswer = inquiryAnswer;
        inquiryAnswer.addinquiry(this);
    }

    //생성 메소드
    public static Inquiry createInquiry(
            Member member,
            String title,
            String content
    ){
        Inquiry inquiry = new Inquiry();
        inquiry.member = member;
        inquiry.title = title;
        inquiry.content = content;
        return inquiry;
    }
}
