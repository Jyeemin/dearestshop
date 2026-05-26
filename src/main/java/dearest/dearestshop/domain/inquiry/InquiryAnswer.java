package dearest.dearestshop.domain.inquiry;

import dearest.dearestshop.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class InquiryAnswer extends BaseTimeEntity {


    @Id @GeneratedValue
    @Column(name = "inquiryanswer_id")
    private Long id;

    @OneToOne(mappedBy = "inquiryAnswer")
    private Inquiry inquiry;

    private String title;

    private String content;

    public InquiryAnswer(Long id, Inquiry inquiry, String title, String content) {
        this.id = id;
        this.inquiry = inquiry;
        this.title = title;
        this.content = content;
    }
}
