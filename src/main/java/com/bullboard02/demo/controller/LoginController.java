package com.bullboard02.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
