package com.bullboard02.demo.entity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BoardTest {
    @Test
    @DisplayName("게시글 생성 확인 테스트")
    void createBoard(){
        /*given*/
        Board board = Board.builder().title("(unit01)title").text("(unit01)text").writerId(2L).build();
        /*when, then*/
        Assertions.assertThat(board.getTitle()).isEqualTo("(unit01)title");
        Assertions.assertThat(board.getText()).isEqualTo("(unit01)text");
    }

    @Test
    @DisplayName("게시글 제목 수정 테스트")
    void changeTitleTest(){
        /*given*/
        Board board = Board.builder().title("(unit01)title").text("(unit01)text").writerId(2L).build();
        /*when*/
        board.changeTitle("(unit01)title_modified");
        /*when, then*/
        Assertions.assertThat(board.getTitle()).isEqualTo("(unit01)title_modified");
    }
}