package com.bullboard02.demo.controller;

import com.bullboard02.demo.DTO.BoardDTO;
import com.bullboard02.demo.DTO.CommentDTO;
import com.bullboard02.demo.DTO.CustomUserDetails;
import com.bullboard02.demo.entity.Board;
import com.bullboard02.demo.entity.Comment;
import com.bullboard02.demo.entity.Member;
import com.bullboard02.demo.service.BoardService;
import com.bullboard02.demo.service.CommentService;
import com.bullboard02.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    
//    @Autowired
//    private CustomUserDetails customUserDetails;
//    CustomUserDetails customUserDetails = new CustomUserDetails();

    @GetMapping("")
    public String boardList(Model model){
        //Board(게시글) 객체
        List<Board> list = boardService.selectAll();

        //boardId : 댓글 갯수 Map
        Map<Long, Long> commentCntMap = new HashMap<>();

        //근데 nickname만 가져오면 되는데 굳이 Member 객체 갖고와야해?
        //아니 그냥 후자가 나을 것 같은 느낌적인 느낌
        Map<Long,String> nicknameMap = new HashMap<>();

        for(Board b: list){
            commentCntMap.put(b.getId(), commentService.countByBoardId(b.getId()));
            nicknameMap.put(b.getId(), customUserDetailsService.MemberById(b.getWriterId()).getNickname());
        }

        model.addAttribute("list",list);
        model.addAttribute("commentCntMap",commentCntMap);
        model.addAttribute("nicknameMap",nicknameMap);

        return "board/showAll";
    }
    //삽입
    @GetMapping("/input")
    public String inputPage(){
        return "board/input";
    }

    @PostMapping("/input")
    public String inputPageProc(BoardDTO boardDTO){
        Board boarddata = new Board();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member curmember = userDetails.getMember();

        //html에서 data를 입력받는 컨트롤러에서 entity로 변환후
        //repostiory메소드에는 entity를 parameter로 넘김
        boarddata.setTitle(boardDTO.getTitle());
        boarddata.setText(boardDTO.getText());
        boarddata.setWriterId(curmember.getId());
        //DateTime !!!!
        boarddata.setCreatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));//날짜 기본값 안되나?

        boardService.persist(boarddata);
        return "redirect:/board";
    }

    @GetMapping("/showOne/{boardid}")
    public String showOne(@PathVariable Long boardid, Model model){
        Board board = boardService.selectOne(boardid);
        String boardWriterNick = customUserDetailsService.MemberById(board.getWriterId()).getNickname();
//        List<Comment> comments = commentService.selectByBoardid(boardid);

        //본댓글 //boardid번 board의 본댓글
        List<Comment> CommentsofBoard = commentService.selectByBoardid(boardid);
        // 대댓글 Map<Long, List<Comment>> rereplys
        // rereplys[parentId]
        Map<Long,List<Comment>> rereplys = new HashMap<>();
        for(Comment reply : CommentsofBoard){
            rereplys.put(reply.getId(),commentService.selectByParentId(reply.getId()));
            for(Comment rereply: rereplys.get(reply.getId())){
                System.out.println("Id(parent)"+reply.getId()+"번의 대댓글-"+rereply.getId());
            }
        }
//        Map<Long,String> replynicknameMap = new HashMap<>();
//
//        for(Comment c: comments){
//            replynicknameMap.put(c.getWriterId(), customUserDetailsService.MemberById(c.getWriterId()).getNickname());
//        }

        //보통은 json으로 넘긴다고? model 인자들을?
        model.addAttribute("board",board);
        model.addAttribute("boardWriterNick",boardWriterNick);
        model.addAttribute("CommentsofBoard",CommentsofBoard);
//        model.addAttribute("replynicknameMap",replynicknameMap);
        return "board/detail";
    }

    @PostMapping("/showOne/{boardid}/commentAdd")
    public String commentAdd(@PathVariable Long boardid,CommentDTO commentDTO){
        //현 login 객체의 id값을 얻기 위해 CustomUserDetails의 멤버변수인 Member로 받음
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member curmember = userDetails.getMember();

        //DTO를 Entity화
        Comment entityC = new Comment();
        entityC.setBoardid(boardid);
        //그냥 본 댓글(대댓글x)
        entityC.setParentId(0L);
        entityC.setWriterId(curmember.getId());
        entityC.setWriterName(curmember.getNickname());
        entityC.setText(commentDTO.getText());
        entityC.setCreatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));//날짜 기본값 안되나?

        System.out.println(entityC.toString());
        //save
        commentService.persist(entityC);
        return "redirect:/board/showOne/"+boardid;
    }
}

