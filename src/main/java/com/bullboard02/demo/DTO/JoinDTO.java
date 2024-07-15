package com.bullboard02.demo.DTO;

import lombok.Data;

@Data
public class JoinDTO {
    private String username;
    private String password;
    private String nickname;
    public String toString(JoinDTO joinDTO){
        return "joinDTO username: "+username+"password: "+password+"nickname: "+nickname;
    }
}
