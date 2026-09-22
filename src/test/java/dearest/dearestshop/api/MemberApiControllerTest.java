package dearest.dearestshop.api;

import dearest.dearestshop.jwt.JwtProvider;
import dearest.dearestshop.repository.MemberRepository;
import dearest.dearestshop.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberApiController.class)
class MemberApiControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MemberService memberService;

    @MockitoBean
    JwtProvider jwtProvider;

    @MockitoBean
    MemberRepository memberRepository;

    @Test
    public void 회원가입_요청_성공() throws Exception{
    //given 회원가입 결과로 1L를 반환한다고 가정
    when(memberService.join(any()))
            .thenReturn(1L);
    //when
        mockMvc.perform(
                post("/api/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"홍길동",
                                "email": "test@test.com",
                                "password": "1234",
                                "passwordConfirm": "1234",
                                "phoneNumber": "01012345678"
                                }
                                """)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("회원가입 성공"))
                .andExpect(jsonPath("$.data").value(1));

    //then
    }


    }


