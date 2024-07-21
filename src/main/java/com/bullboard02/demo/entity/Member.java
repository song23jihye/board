package com.bullboard02.demo.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
///https://blog.naver.com/rorean/221587154921
@Data //이거 entity에도 필요한가?
@NoArgsConstructor
@Builder //어노테이션 사용을 위해서는 전체 필드를 포함하는 생성자 필요
@AllArgsConstructor //-> 그래서 존재
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable=false,updatable = false)
    private String username;

    @NonNull
    @Column(nullable=false,updatable = true)
    private String password;

    @Column(nullable = true,updatable = true)
    private String nickname;

    @Column
    private String role; //디폴트 "ROLE_USER"

}
