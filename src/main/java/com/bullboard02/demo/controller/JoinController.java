package com.bullboard02.demo.controller;

import com.bullboard02.demo.DTO.JoinDTO;
import com.bullboard02.demo.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {
    @Autowired
    private JoinService joinService;

    @GetMapping("/join")
    public String joinP(){
        return "signin";
    }

    @PostMapping("joinProc")
    public String joinProc(JoinDTO joinDTO){
        joinService.register(joinDTO);
        return("redirect:/login");
    }
}