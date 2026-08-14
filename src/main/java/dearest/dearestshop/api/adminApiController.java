package dearest.dearestshop.api;

import dearest.dearestshop.dto.memberdto.MemberResponseDto;
import dearest.dearestshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class adminApiController {

    private final MemberService memberService;

    /**
     * 회원목록조회
     */
    @GetMapping("/members")
    public ResponseEntity<ApiResponse<List<MemberResponseDto>>> members(){
        List<MemberResponseDto> members = memberService.findAll();
        ApiResponse<List<MemberResponseDto>> response = new ApiResponse<>(
                true,
                "회원목록 조회",
                members
        );
        return ResponseEntity.ok(response);
    }


}
