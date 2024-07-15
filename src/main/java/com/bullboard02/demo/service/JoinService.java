package com.bullboard02.demo.service;

import com.bullboard02.demo.DTO.JoinDTO;
import com.bullboard02.demo.entity.Member;
import com.bullboard02.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //securityConfig파일에서 Bean으로 등록해둠
    @Autowired
    MemberRepository memberRepository;
    public void register(JoinDTO joinDTO){
        boolean isuser = memberRepository.existsByUsername(joinDTO.getUsername());
        if(isuser){
            return;
        }
        Member data = new Member();

        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setNickname(joinDTO.getNickname());
        data.setRole("ROLE_USER"); //처음은 "ROLE_ADMIN"

        memberRepository.save(data); //jpa에서 data등록
        System.out.println("joinDTO.getUsername()"+joinDTO.getUsername());
    }

}
