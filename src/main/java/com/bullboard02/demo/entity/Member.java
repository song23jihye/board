package com.bullboard02.demo.entity;
import jakarta.persistence.*;
import lombok.*;
///https://blog.naver.com/rorean/221587154921
@Data//이거 entity에도 필요한가?
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private String role;
}
