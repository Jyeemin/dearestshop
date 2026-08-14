package dearest.dearestshop.service;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.dto.memberdto.MemberJoinDto;
import dearest.dearestshop.dto.memberdto.MemberLoginDto;
import dearest.dearestshop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
    //given
        MemberJoinDto dto = new MemberJoinDto();
        dto.setName("kim");
    //when
        Long savedId = memberService.join(dto);
        Member findMember = memberRepository.findById(savedId).get();

        //then
        Assertions.assertEquals("kim",findMember.getName());

    }


    @Test
    public void 중복_회원_예외() throws Exception{
    //given
        MemberJoinDto dto1 = new MemberJoinDto();
        dto1.setEmail("kim");  dto1.setPassword("1234");

        MemberJoinDto dto2 = new MemberJoinDto();
        dto2.setEmail("kim");  dto2.setPassword("1234");

    //when
        memberService.join(dto1);
    //then
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            memberService.join(dto2); //예외가 발생해야 함
        });
        Assertions.assertEquals("이미 가입된 이메일",runtimeException.getMessage());

    }

    @Test
    public void 로그인() throws Exception{
    //given
        MemberJoinDto joindto = new MemberJoinDto();
        joindto.setEmail("!234"); joindto.setPassword("1234");
        Long join = memberService.join(joindto);
        Member member = memberRepository.findById(join).get();

        MemberLoginDto logindto = new MemberLoginDto();
        logindto.setEmail(joindto.getEmail());
        logindto.setPassword(joindto.getPassword());


        //when
        //Long login = memberService.login(logindto);

        //then
        //Assertions.assertEquals(member.getId(),login);
    }


    @Test
    public void 로그인_이메일_없음() throws Exception{
    //given
        MemberLoginDto logindto = new MemberLoginDto();
    //when
        //Long login = memberService.login(logindto);
    //then
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            memberService.login(logindto); //예외가 발생해야 함
        });
        Assertions.assertEquals("존재하지 않는 이메일입니다.",runtimeException.getMessage());

    }


    }













