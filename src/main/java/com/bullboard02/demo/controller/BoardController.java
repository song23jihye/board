package com.bullboard02.demo.controller;

import com.bullboard02.demo.DTO.BoardDTO;
import com.bullboard02.demo.DTO.CustomUserDetails;
import com.bullboard02.demo.entity.Board;
import com.bullboard02.demo.entity.Member;
import com.bullboard02.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;
//    @Autowired
//    private CustomUserDetails customUserDetails;
//    CustomUserDetails customUserDetails = new CustomUserDetails();

    @GetMapping("/board")
    public String boardList(Model model){
        List<Board> list = boardService.selectAll();
        model.addAttribute("list",list);
        return "board/showAll";
    }
    //삽입
    @GetMapping("/board/input")
    public String inputPage(){
        return "board/input";
    }

    @PostMapping("/board/input")
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

    @GetMapping("/board/showOne/{boardid}")
    public String showOne(@PathVariable Long boardid,Model model){
        Board board = boardService.selectOne(boardid);
        model.addAttribute("board",board);
        return "redirect:/board/detail";
    }
}

