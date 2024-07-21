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
        //작성자 Id set
        //Board(WriterId) -- Member(Id) -> nickname 조회
        //Map에 작성자 ID : Nickname // 더 나은 로직이 뭘까?
        Map<Long,String> nicknameByWriterId = new HashMap();
        for(Board b : list){
            Long WRITER_ID = b.getWriterId();
            String nickname = customUserDetailsService.MemberById(WRITER_ID).getNickname();
            System.out.printf("%d : %s", WRITER_ID,nickname);
            nicknameByWriterId.put(WRITER_ID,nickname);
        }
        //댓글수
        List<Long> commentCntByboardId = new ArrayList<>();
        for(Board b : list){
            //댓글 수
            Long commentCntTmp = (commentService.countByBoardId(b.getId()));
            commentCntByboardId.add(commentCntTmp);
            System.out.printf("%d ",commentCntTmp);
        }

        model.addAttribute("list",list);
        model.addAttribute("commentCntByboardId",commentCntByboardId);//댓수배열
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

        boardService.persist(boarddata);
        return "redirect:/board";
    }

    @GetMapping("/showOne/{boardid}")
    public String showOne(@PathVariable Long boardid, Model model){
        Board board = boardService.selectOne(boardid);
        List<Comment> comments = commentService.selectByBoardid(boardid);
        //게시글에 해당하는 댓글들
        for(Comment c : comments){
            System.out.println("writerId:"+c.getWriterId()+", text:"+c.getText());
        }
        model.addAttribute("board",board);
        model.addAttribute("comments",comments);
        return "board/detail";
    }
//    @GetMapping("/showOne/commentAdd/{boardid}")
//    public String commentAddShow(@PathVariable Long boardid){
//        System.out.println("get post 방식 문제 - post방식이 지원이 안된다고? th에서?");
//        System.out.println("boardid는 "+boardid);
//        return "board";
//    }
    @PostMapping("/showOne/{boardid}/commentAdd")
    public String commentAdd(@PathVariable Long boardid,CommentDTO commentDTO){
        //현 login 객체의 id값을 얻기 위해 CustomUserDetails의 멤버변수인 Member로 받음
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member curmember = userDetails.getMember();
        //DTO를 Entity화
        Comment entityC = new Comment();
        entityC.setBoardid(boardid);
        entityC.setWriterId(curmember.getId());
        entityC.setWriterName(curmember.getNickname());
        entityC.setText(commentDTO.getText());
        //save
        //test02 - commentDTO의 text
//        System.out.println("입력댓글: "+entityC.getText());
//        System.out.println("댓글 boardid: "+entityC.getBoardid());
//        System.out.println("댓글 writerId: "+entityC.getWriterId());
//        System.out.println("댓글 writerName: "+entityC.getWriterName());
        commentService.persist(entityC);
        return "redirect:/board/showOne/"+boardid;
    }
}

