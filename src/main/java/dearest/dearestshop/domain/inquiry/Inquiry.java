package dearest.dearestshop.domain.inquiry;

import dearest.dearestshop.domain.BaseTimeEntity;
import dearest.dearestshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Inquiry extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "INQUIRY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiryanswer_id")
    private InquiryAnswer inquiryAnswer;

    private String title;

    @Column(length = 3000)
    private String content;

    public Inquiry(Long id,
                   Member member,
                   InquiryAnswer inquiryAnswer,
                   String title,
                   String content) {
        this.id = id;
        this.member = member;
        this.inquiryAnswer = inquiryAnswer;
        this.title = title;
        this.content = content;
    }
}
