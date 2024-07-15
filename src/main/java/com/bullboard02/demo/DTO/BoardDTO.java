package com.bullboard02.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private String title;
    private String text;
    private String writerId;
    private String writerNickname;

}
