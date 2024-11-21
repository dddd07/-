package com.icia.semi.controller;

import com.icia.semi.dto.AnswerDTO;
import com.icia.semi.dto.BoardDTO;
import com.icia.semi.dto.BoardEntity;
import com.icia.semi.dto.SearchDTO;
import com.icia.semi.service.AnswerService;
import com.icia.semi.service.BoardService;
import com.icia.semi.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestfulController {

    private final MemberService msvc;
    private final BoardService bsvc;


    // idCheck : 아이디 중복 체크
    @PostMapping("/idCheck")
    public String idCheck(@RequestParam("SId") String SId) {
        String result = msvc.idCheck(SId);
        return result;
    }

    // emailCheck : 이메일 인증번호 받아오기
    @PostMapping("/emailCheck")
    public String emailCheck(@RequestParam("SEmail") String SEmail) {
        String uuid = msvc.emailCheck(SEmail);
        return uuid;
    }

    // boardList : 게시글 목록
    @PostMapping("/boardList")
    public List<BoardDTO> boardList() {
        return bsvc.boardList();
    }

    // searchList : 검색 목록
    @PostMapping("/searchList")
    public List<BoardDTO> searchList(@ModelAttribute SearchDTO search) {
        System.out.println("search : " + search);   // category, keyword
        return bsvc.searchList(search);
    }

    // 마이프로필 추가 부분
    // mBoardList : 나의 질문 목록
    @PostMapping("mPostList")
    public List<BoardDTO> mPostList(HttpSession session, @ModelAttribute BoardDTO board){
        System.out.println("[1] 나의 질문 : " + board);

        // 세션에서 로그인된 SID를 가져오기
        String loginId = (String) session.getAttribute("loginId");

        // 로그인되지 않은 경우
        if (loginId == null) {
            return new ArrayList<>();
        }

        // BoardDTO에 로그인된 SID를 설정
        board.setSId(loginId);

        return bsvc.mBoardList(board);
    }

    private final AnswerService asvc;

    // mAnswerList : 나의 답변 목록
    @PostMapping("mAnswerList")
    public List<AnswerDTO> mAnswerList(HttpSession session, @ModelAttribute AnswerDTO answer){
        System.out.println("[1] 나의 답변 : " + answer);

        // 세션에서 로그인된 SID를 가져오기
        String loginId = (String) session.getAttribute("loginId");

        // 로그인되지 않은 경우
        if (loginId == null) {
            return new ArrayList<>(); // 로그인되지 않은 경우 빈 리스트 반환 (또는 적절한 오류 처리)
        }

        // AnswerDTO에 로그인된 SId를 설정
        answer.setSId(loginId);

        return asvc.mAnswerList(answer);
    }


}