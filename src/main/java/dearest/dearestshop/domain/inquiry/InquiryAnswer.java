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
public class InquiryAnswer extends BaseTimeEntity {


    @Id @GeneratedValue
    @Column(name = "inquiryanswer_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    private String title;

    private String content;

    // 생성 메소드
    public static InquiryAnswer createInquiryAnswer(
            String title,
            String content
    ) {
        InquiryAnswer answer = new InquiryAnswer();
        answer.title = title;
        answer.content = content;
        return answer;
    }

    //편의 메서드
    public void addinquiry(Inquiry inquiry){
        this.inquiry = inquiry;
    }
}
