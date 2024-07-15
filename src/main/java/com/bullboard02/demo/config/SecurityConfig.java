package com.bullboard02.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;

@Configuration //Config파일로 설정해주는 @
@EnableWebSecurity //WebSecurity 활성화 @
public class SecurityConfig {
    //httpSecurity
//    스프링 시큐리티의 설정은 HttpSecurity로 한다.
//        리소스(URL) 접근 권한 설정
//    인증 전체 흐름에 필요한 Login, Logout 페이지 인증완료 후 페이지 인증 실패 시 이동 페이지 설정
//    인증 로직을 커스텀하기 위한 커스텀 필터 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf((auth) -> auth.disable());
        httpSecurity
                .authorizeHttpRequests((auth)->auth
                .requestMatchers("/join","/login","/joinProc").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/","/main","/board","/my/**").hasAnyRole("ADMIN","USER") //마이페이지
                .anyRequest().authenticated()
                //순서
        );
        //기존 formLogin방식
        httpSecurity.formLogin((auth)
                        ->auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
        );
        //https://velog.io/@hyeonjoonpark/Spring-Security-BCrypt

        //login방식 (2)
        //httpSecurity.httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

}
