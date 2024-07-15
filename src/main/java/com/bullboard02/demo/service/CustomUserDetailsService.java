package com.bullboard02.demo.service;

import com.bullboard02.demo.DTO.CustomUserDetails;
import com.bullboard02.demo.entity.Member;
import com.bullboard02.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    //user가 저장되어있는 repository를 Autowired를 통해 주입
    //(간단히 필드주입-생성자 주입 권장)
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberData = memberRepository.findByUsername(username);//repostiory를 통해 db 접근하고 전달받음
        if(memberData!=null){
            return new CustomUserDetails(memberData);
            //username만 검증해서 데이터가 존재하면 DTO를 새로 생성하는 거야?
            //->CustomUserDetails클래스에 Member(Entity)객체 parameter로 한 생성자 존재해야함
            //비밀번호 검증은?? loginProc은?
        }
        return null;
    }
}
