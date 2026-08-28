package dearest.dearestshop.api;

import dearest.dearestshop.dto.memberdto.LoginResponseDto;
import dearest.dearestshop.dto.memberdto.MemberJoinDto;
import dearest.dearestshop.dto.memberdto.MemberLoginDto;
import dearest.dearestshop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원가입
     * @param dto
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<Long>> joinMember(@RequestBody @Valid MemberJoinDto dto){
        Long joinId = memberService.join(dto);
        System.out.println("Service 완료 : " + joinId);

        ApiResponse<Long> response = new ApiResponse<>(
                true,
                "회원가입 성공",
                joinId
        );

        return ResponseEntity.ok(response);
    }

    /**
     * 로그인
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> loginMember(@RequestBody MemberLoginDto dto){
        LoginResponseDto loginResponse = memberService.login(dto);
        ApiResponse<LoginResponseDto> response = new ApiResponse<>(
                true,
                "로그인 성공",
                loginResponse
        );

        return ResponseEntity.ok(response);
    }



}


