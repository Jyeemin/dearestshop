package dearest.dearestshop.service;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.dto.memberdto.MemberJoinDto;
import dearest.dearestshop.dto.memberdto.MemberLoginDto;
import dearest.dearestshop.jwt.JwtProvider;
import dearest.dearestshop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 회원가입 성공
 * 이메일 중복 회원가입 실패
 * 비밀번호 암호화 확인
 * 회원 조회
 * 존재하지 않는 회원 조회
 * 로그인
 * 잘못된 비밀번호
 */

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtProvider jwtProvider;

    @InjectMocks
    MemberService memberService;

    @Test
    public void 회원가입() throws Exception{
    //given
        MemberJoinDto dto = new MemberJoinDto("홍길동","test@test.com","1234","1234","01012341234");
    //when
        when(memberRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword()))
                .thenReturn("encodedPassword");

        memberService.join(dto);
    //then
        verify(memberRepository).save(any(Member.class));
    }


    @Test
    public void 이메일중복_가입실패() throws Exception{
    //given
        MemberJoinDto dto = new MemberJoinDto("홍길동","test@test.com","1234","1234","01012341234");

        when(memberRepository.existsByEmail(dto.getEmail()))
                .thenReturn(true);
    //when

    //then
        assertThrows(
                RuntimeException.class,
                () -> memberService.join(dto)
        );
    }

    @Test
    public void 비밀번호_불일치실패() throws Exception{
    //given
        MemberJoinDto dto = new MemberJoinDto("홍길동","test@test.com","1234","5678","01012341234");

    //when
        when(memberRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);
    //then
        assertThrows(RuntimeException.class,
                () -> memberService.join(dto));
    }


    @Test
    public void 비밀번호암호화_확인() throws Exception{
    //given
        MemberJoinDto dto = new MemberJoinDto("홍길동","test@test.com","1234","1234","01012341234");

        when(memberRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword()))
                .thenReturn("encodedPassword");
    //when
        memberService.join(dto);
    //then
        //save에 실제로 전달된 Member를 가져온다
        ArgumentCaptor<Member> captor =
                ArgumentCaptor.forClass(Member.class);

        verify(memberRepository)
                .save(captor.capture());

        Member savedMember = captor.getValue();
        assertEquals("encodedPassword", savedMember.getPassword());

    }


    }










































