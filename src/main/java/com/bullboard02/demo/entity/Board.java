package com.bullboard02.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false,updatable = false)
    private Long writerId;

    @NonNull
    @Column(nullable = false,length=30)
    private String title;

    @NonNull
    @Column(length=60)
    private String text;

    @Column//@Temporal을 생략하면 자바의 Date와 가장 유사한 timestamp로 정의됩니다.
    @CreationTimestamp
    private LocalDateTime entryDate;

    @Column
    @CreationTimestamp
    private LocalDateTime modifyDate; // 날짜와 시간

    public void changeTitle(String title){
        this.title = title;
    }//unit test에서 Assertion 테스트


}
