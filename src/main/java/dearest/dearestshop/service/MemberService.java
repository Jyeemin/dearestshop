package dearest.dearestshop.service;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.dto.LoginResponseDto;
import dearest.dearestshop.dto.MemberJoinDto;
import dearest.dearestshop.dto.MemberLoginDto;
import dearest.dearestshop.jwt.JwtProvider;
import dearest.dearestshop.repository.MemberRepository;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(MemberJoinDto dto){
        //이메일 검증
        if(memberRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException(("이미 가입된 이메일입니다."));
        }

        //비밀번호 검증
        if(!dto.getPassword().equals(dto.getPasswordConfirm())){
            throw new RuntimeException("비밀번호가 서로 다릅니다.");
        }


        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Member member = Member.createMember(
                dto.getName(),
                dto.getEmail(),
                encodedPassword,//암호화
                dto.getPhoneNumber()
        );

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 로그인
     */
    public LoginResponseDto login(MemberLoginDto dto){
        //이메일 확인
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("존재하지 않는 이메일입니다."));

        //비밀번호 확인
        if(!passwordEncoder.matches(
                dto.getPassword(),
                member.getPassword()
        ))
        {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        //JWT생성
        String token = jwtProvider.createToken(member.getEmail());

        return new LoginResponseDto(token);
    }

    /**
     * 아이디 찾기
     * (이메일을 입력받아 일치 시 해당 이메일로 아이디 전송)
     */


    /**
     * 회원 전체 조회
     */
    public List<Member> findAll(){
        return memberRepository.findAll();
    }
}
