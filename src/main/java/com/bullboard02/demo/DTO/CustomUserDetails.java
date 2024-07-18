package com.bullboard02.demo.DTO;

import com.bullboard02.demo.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private Member member;

    public Member getMember() {
        return member;
    }

    public CustomUserDetails(Member member){ //UserDetailService에서 갖다쓰는 생성자
        this.member=member;
    }
    //CustomUserDetailsService에서 return 해준 객체가 이 생성자를 통함
    //security config에게 전달하는 부분은?
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //role return
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority(){
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return collection;//요걸 안함
    }
    //요걸 안함
    @Override
    public String getPassword() {
        return member.getPassword();
    }
    //요걸 안함
    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
//        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
//        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
//        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return true;
//        return UserDetails.super.isEnabled();
    }
}
