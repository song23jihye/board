package com.bullboard02.demo.controller;
import com.bullboard02.demo.DTO.CustomUserDetails;
import com.bullboard02.demo.entity.Member;
import com.bullboard02.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/")
    public String mainP(){
        return("main");
    }
    @GetMapping("/mypage")//"/mypage/{cusid}"
    public String myPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member curmember = userDetails.getMember();
        model.addAttribute("curmember",curmember);
        return("user/mypage");
    }
}
//        System.out.println("authentication : " + authentication);
//        System.out.println("principal : " + authentication.getPrincipal());
//        System.out.println("userDetail : " + userDetails);
//        System.out.println("toString : " + userDetails.toString());
//        System.out.print("getmember 메소드로 받아온 nickname: ");
//        System.out.println(curmember.getNickname());