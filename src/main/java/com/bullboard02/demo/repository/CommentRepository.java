package com.bullboard02.demo.repository;

import com.bullboard02.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment getById(Long id);

    //난이도 up - 내가 쓴 댓글 보기
    Comment getByWriterId(Long writerId);
    @Override
    List<Comment> findAll();

    List<Comment> findAllByBoardid(Long boardId);

    //long countCommentOfBoard(Long boardId);
    //이렇게 좆대로 말고 밑처럼 규칙 따르렴
    Long countByBoardid(Long boardId);

    @Override
    long count();
}
