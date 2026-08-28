package dearest.dearestshop.jwt;

import dearest.dearestshop.domain.member.Member;
import dearest.dearestshop.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.요청에서 Authorization 헤더 가져오기
        System.out.println("===== JWT FILTER 실행 =====");
        System.out.println("요청 주소 = " + request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        System.out.println("Authorization = " + authorization);

        //2.JWT가 없으면 그냥 다음 단계로 보내기
        if(authorization == null || !authorization.startsWith("Bearer ")){
            System.out.println("JWT 없음");
            filterChain.doFilter(request,response);
            return;
        }

        //3."Bearer "를 제외하고 실제 JWT만 가져오기
        String token = authorization.substring(7);

        //4. JWT가 정상인지 확인
        if (jwtProvider.validateToken(token)) {

            //5.JWT에서 이메일 가져오기
            String email = jwtProvider.getEmail(token);
            System.out.println("JWT인증 성공: " + email);
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("회원을 찾을 수 없습니다.")
            );

            System.out.println("회원 이름: " + member.getName());
            System.out.println("회원 권한: " + member.getRole());

            String role = member.getRole().name();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    member.getEmail(),
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + role))
            );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);


        }else{
            System.out.println("JWT 인증 실패");
        }
        //6.다음 Filter로 요청 보내기
        filterChain.doFilter(request,response);



    }
}
