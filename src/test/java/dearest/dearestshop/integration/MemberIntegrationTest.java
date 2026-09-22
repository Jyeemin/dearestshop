package dearest.dearestshop.integration;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.dto.memberdto.LoginResponseDto;
import dearest.dearestshop.dto.memberdto.MemberJoinDto;
import dearest.dearestshop.dto.memberdto.MemberLoginDto;
import dearest.dearestshop.jwt.JwtProvider;
import dearest.dearestshop.repository.MemberRepository;
import dearest.dearestshop.service.MemberService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



@SpringBootTest
@Transactional
public class MemberIntegrationTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        MemberJoinDto dto = new MemberJoinDto("홍길동", "test@test.com", "1234", "1234", "01012341234");

        //when
        Long memberId = memberService.join(dto);

        //then
        Member findMember = memberRepository.findById(memberId).orElseThrow();
        assertThat(findMember.getName()).isEqualTo("홍길동");

        assertThat(findMember.getEmail()).isEqualTo("test@test.com");

        assertThat(passwordEncoder.matches(
                "1234",
                findMember.getPassword()
        )).isTrue();

    }

    @Test
    public void 이메일중복_회원가입실패() throws Exception{
    //given
        MemberJoinDto dto1 = new MemberJoinDto("홍길동", "test@test.com", "1234", "1234", "01012341234");
        MemberJoinDto dto2 = new MemberJoinDto("홍길동", "test@test.com", "1234", "1234", "01012341234");

    //when
        memberService.join(dto1);
    //then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> memberService.join(dto2));
        assertThat(exception.getMessage())
                .isEqualTo("이미 가입된 이메일입니다.");
    }

    @Test
    public void 비밀번호불일치_회원가입실패() throws Exception{
    //given
        MemberJoinDto dto = new MemberJoinDto("홍길동", "test@test.com", "1234", "4567", "01012341234");
    //when

    //then
        assertThrows(RuntimeException.class,
                () -> memberService.join(dto));
    }

    @Test
    public void 비밀번호암호화_성공() throws Exception{
    //given
        MemberJoinDto dto = new MemberJoinDto("홍길동", "test@test.com", "1234", "1234", "01012341234");
        Long memberId = memberService.join(dto);

        //when
        Member findMember = memberRepository.findById(memberId).orElseThrow();

        //then
        assertThat(passwordEncoder.matches(dto.getPassword(),findMember.getPassword())).isTrue();

    }


    @Test
    public void 회원_조회() throws Exception{
    //given
        Member member = Member.createMember("홍길동", "test@test.com", "encodedPassword", "01012341234");
        memberRepository.save(member);
        //when
        Member result = memberService.findOne("test@test.com");
        //then
        assertEquals("홍길동",result.getName());
        assertEquals("test@test.com",result.getEmail());
    }

    @Test
    public void 미존재회원_조회실패() throws Exception{
    //given

    //when

    //then
        assertThrows(RuntimeException.class,
                () -> memberService.findOne("notfound@test.com"));
    }

    @Test
    public void 로그인_성공() throws Exception{
    //given
        MemberJoinDto dto = new MemberJoinDto("홍길동", "test@test.com", "1234", "1234", "01012341234");
        Long memberId = memberService.join(dto);
        MemberLoginDto logindto = new MemberLoginDto("test@test.com", "1234");
        //when
        LoginResponseDto findLogin = memberService.login(logindto);

        //then
        assertEquals("홍길동",findLogin.getMemberName());
    }

    @Test
    public void 로그인_실패() throws Exception{
    //given
        MemberJoinDto dto = new MemberJoinDto("홍길동", "test@test.com", "1234", "1234", "01012341234");
        Long memberId = memberService.join(dto);
        MemberLoginDto logindto = new MemberLoginDto("test@test.com", "4567");
        //when

    //then
        assertThrows(RuntimeException.class,
                () -> memberService.login(logindto));
    }



    @Test
    public void jwt_확인() throws Exception{
    //given
        MemberJoinDto dto =
                new MemberJoinDto(
                        "홍길동",
                        "test@test.com",
                        "1234",
                        "1234",
                        "01012341234"
                );

        memberService.join(dto);

        MemberLoginDto loginDto =
                new MemberLoginDto(
                        "test@test.com",
                        "1234"
                );



        //when
        LoginResponseDto loginResponse =
                memberService.login(loginDto);
    //then
        assertThat(loginResponse.getToken())
                .isNotNull();

        assertThat(loginResponse.getToken())
                .isNotBlank();

        String email =
                jwtProvider.getEmail(loginResponse.getToken());

        assertThat(email)
                .isEqualTo("test@test.com");

    }


    }
































