package dearest.dearestshop.repository;

import dearest.dearestshop.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원_저장() throws Exception{
    //given
        Member member = Member.createMember(
                "홍길동",
                "test@test.com",
                "1234",
                "01012345678"
        );

    //when
        Member savedMember = memberRepository.save(member);

        //then
        assertNotNull(savedMember.getId());
        assertEquals("홍길동", savedMember.getName());
        assertEquals("test@test.com", savedMember.getEmail());
    }

    @Test
    public void 이메일_회원_조회() throws Exception{
    //given
        Member member = Member.createMember(
                "홍길동",
                "test@test.com",
                "1234",
                "01012345678"
        );
        memberRepository.save(member);
    //when
        Optional<Member> result = memberRepository.findByEmail("test@test.com");

        //then
        assertTrue(result.isPresent());
        assertEquals("홍길동",result.get().getName());
    }


    @Test
    public void 이메일존재_확인() throws Exception{
    //given
        Member member = Member.createMember(
                "홍길동",
                "test@test.com",
                "1234",
                "01012345678"
        );
        memberRepository.save(member);
    //when

        boolean result = memberRepository.existsByEmail("test@test.com");
        //then
        assertTrue(result);
    }


    @Test
    public void 회원이름으로_검색() throws Exception{
    //given
        Member member1 = Member.createMember(
                "홍길동",
                "hong@test.com",
                "1234",
                "01012345678"
        );
        memberRepository.save(member1);

        Member member2 = Member.createMember(
                "홍길순",
                "soon@test.com",
                "1234",
                "01012345678"
        );
        memberRepository.save(member2);
    //when
        List<Member> result = memberRepository.findByNameContaining("홍");

        //then
        assertEquals(2, result.size());
    }


    }











