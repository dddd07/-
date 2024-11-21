package com.icia.semi.controller;

import com.icia.semi.dao.MemberRepository;
import com.icia.semi.dto.BoardDTO;
import com.icia.semi.dto.MemberDTO;
import com.icia.semi.dto.MemberEntity;
import com.icia.semi.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController<principaldetail> {

    private final MemberService msvc;

    private final HttpSession session;

    // index : 처음페이지로 이동
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    // mJoinForm : 회원가입 페이지로 이동
    @GetMapping("/mJoinForm")
    public String mJoinForm() {
        return "join";
    }

    // mLoginForm : 로그인 페이지로 이동
    @GetMapping("/mLoginForm")
    public String mLoginForm() {
        return "login";
    }

    // mJoin : 회원가입
    @PostMapping("/mJoin")
    public ModelAndView mJoin(@ModelAttribute MemberDTO member) {
        System.out.println("\n회원가입 메소드\n[1] html → controller : " + member);
        return msvc.mJoin(member);
    }

    // mLogin : 로그인
    @PostMapping("/mLogin")
    public ModelAndView mLogin(@ModelAttribute MemberDTO member) {
        System.out.println("로그인 실험 1번 member >> " + member);
        return msvc.mLogin(member);
    }

    // mLogout : 로그아웃
    @GetMapping("/mLogout")
    public String mLogout() {
        session.invalidate();
        return "index";
    }

    // myProfile : 마이프로필 페이지로 이동 및 내 정보 보기
    @GetMapping("/myProfile")
    public ModelAndView myProfile(HttpSession session) {
        System.out.println("[1] 마이프로필 페이지로 이동");

        // 세션에서 로그인된 SID를 가져오기
        String loginId = (String) session.getAttribute("loginId");

        // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        if (loginId == null) {
            return new ModelAndView("redirect:/index");  // 인덱스로 리다이렉트
        }

        // SID를 사용하여 마이프로필 정보 조회
        return msvc.myProfile(loginId);  // SID로 프로필 정보 조회
    }

    // modify : 내 정보 수정
    @PostMapping("/modify")
    public ModelAndView modify(HttpSession session, @ModelAttribute MemberDTO member) {
        System.out.println("[1] 내 정보 수정 완료 : " + member);

        // 세션에서 로그인된 SID를 가져오기
        String loginId = (String) session.getAttribute("loginId");

        // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        if (loginId == null) {
            return new ModelAndView("redirect:/index");  // 인덱스로 리다이렉트
        }

        // 로그인된 사용자의 SID를 회원 수정 DTO에 셋팅
        member.setSId(loginId);  // 세션에서 가져온 loginId를 DTO에 설정

        return msvc.modify(member);
    }

    // mDelete : 회원탈퇴
    @GetMapping("/mDelete")
    public ModelAndView mDelete(@ModelAttribute MemberDTO member) {
        System.out.println("회원 삭제" + member);
        return msvc.mDelete(member);
    }

    // postView : 나의 질문 페이지로 이동
    @GetMapping("/postView")
    public String postView() {
        return "postView";
    }

    // answerView : 나의 답변 페이지로 이동
    @GetMapping("/answerView")
    public String answerView() {
        return "answerView";
    }

}
