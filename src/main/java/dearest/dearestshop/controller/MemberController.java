package dearest.dearestshop.controller;

import dearest.dearestshop.dto.MemberJoinDto;
import dearest.dearestshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/account")
    public String Account(MemberJoinDto dto){
        log.info("account");
        return "members/account";
    }




}
