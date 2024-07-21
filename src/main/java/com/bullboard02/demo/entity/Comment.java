package com.bullboard02.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키설정필요
    @NonNull
    @Column(nullable = false,updatable = false)
    private Long boardid;

    @NonNull
    @Column(nullable = false,updatable = false)
    private Long writerId;

    //외래키 설정 - 위 writerId로 조회해서 갖고 올 건데..
    @NonNull
    @Column(nullable = false)
    private String writerName;

    @NonNull
    @Column(nullable = false, length=50)
    private String text;

    @NonNull
    @Column
    @CreationTimestamp
    private LocalDateTime entryDate;

    @NonNull
    @Column
    @CreationTimestamp
    private LocalDateTime modifyDate;

}
