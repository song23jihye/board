package com.bullboard02.demo.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false,updatable = false)
    private Long parentId;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime createdDate;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime modifiedDate;

}
