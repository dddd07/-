package com.icia.semi.controller;

import com.icia.semi.dto.CommentDTO;
import com.icia.semi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService csvc;

    @PostMapping("/cList")
    public List<CommentDTO> cList(@RequestParam("PNUM") int PNUM) {
        System.out.println("\n댓글 목록\n[1]html → controller : " + PNUM);
        return csvc.cList(PNUM);
    }

    // cWrite, cModify
    @PostMapping("/SID")
    public List<CommentDTO> SID(@ModelAttribute CommentDTO comment) {
        System.out.println("\n댓글 작성\n[1]html → controller : " + comment);
        return csvc.SID(comment);
    }

    // cDelete
    @PostMapping("/cDelete")
    public List<CommentDTO> cDelete(@ModelAttribute CommentDTO comment) {
        System.out.println("\n댓글 삭제\n[1]html → controller : " + comment);
        return csvc.cDelete(comment);
    }
}
