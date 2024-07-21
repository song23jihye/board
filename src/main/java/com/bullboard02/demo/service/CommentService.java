package com.bullboard02.demo.service;

import com.bullboard02.demo.entity.Comment;
import com.bullboard02.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //뭐하는데 쓰는 어노테이션?
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public void persist(Comment comment){
        commentRepository.save(comment);
    }
    public Comment selectOne(Long id){
        return commentRepository.getById(id);
    }
    public List<Comment> selectByBoardid(Long boardid){
        return commentRepository.findAllByBoardid(boardid);
    }
    public Long countByBoardId(Long boardid){
        return commentRepository.countByBoardid(boardid);
        //게시글의 댓글 갯수
    }
    public Long countCnt(){
        return commentRepository.count();
    }
}
