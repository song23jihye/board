package com.bullboard02.demo.controller;

import com.bullboard02.demo.entity.Member;
import com.bullboard02.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginP(){
        return "login";
    }

//    @PostMapping("/loginProc")
//    public String loginProcess(Member member){
////        CustomUserDetails data = customUserDetailsService.loadUserByUsername(member.getUsername());
//        System.out.println("login Trial");
//        return("redirect:/board");
//    }

}
