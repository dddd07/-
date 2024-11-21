package com.icia.semi.controller;

import com.icia.semi.dto.BoardDTO;
import com.icia.semi.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller // Spring의 컨트롤러 레이어를 나타내는 애너테이션
@RequiredArgsConstructor // Lombok을 사용하여 final 필드에 대한 생성자를 자동으로 생성
public class BoardController {

    private final BoardService bsvc; // BoardService 객체를 주입받기 위한 필드

    @GetMapping("/about") // "about" 페이지로의 GET 요청을 처리
    public String about() {
        return "about"; // "about" 뷰 페이지로 이동
    }

    @GetMapping("/service") // "service" 페이지로의 GET 요청을 처리
    public String service() {
        return "service"; // "service" 뷰 페이지로 이동
    }

    @GetMapping("/blog") // "blog" 페이지로의 GET 요청을 처리
    public String blog() {
        return "blog"; // "blog" 뷰 페이지로 이동
    }

    @GetMapping("/feature") // "feature" 페이지로의 GET 요청을 처리
    public String feature() {
        return "feature"; // "feature" 뷰 페이지로 이동
    }

    @GetMapping("/team") // "team" 페이지로의 GET 요청을 처리
    public String team() {
        return "testimonial"; // "testimonial" 뷰 페이지로 이동
    }

    @GetMapping("/testimonial") // "testimonial" 페이지로의 GET 요청을 처리
    public String testimonial() {
        return "offer"; // "offer" 뷰 페이지로 이동
    }

    @GetMapping("/offer") // "offer" 페이지로의 GET 요청을 처리
    public String offer() {
        return "404"; // "404" 뷰 페이지로 이동
    }

    @GetMapping("/FAQ") // "FAQ" 페이지로의 GET 요청을 처리
    public String FAQ() {
        return "FAQ"; // "FAQ" 뷰 페이지로 이동
    }

    @GetMapping("/login") // "login" 페이지로의 GET 요청을 처리
    public String login() {
        return "login"; // "login" 뷰 페이지로 이동
    }

    @GetMapping("/admin") // "admin" 페이지로의 GET 요청을 처리
    public String admin() {
        return "admin"; // "admin" 뷰 페이지로 이동
    }

    @GetMapping("/register") // "register" 페이지로의 GET 요청을 처리
    public String register() {
        return "register"; // "register" 뷰 페이지로 이동
    }

    // 게시글 작성 페이지로 이동하는 메서드
    @GetMapping("/pWriteForm")
    public String pWriteForm() {
        return "write"; // "write"라는 뷰 페이지로 이동
    }

    // 게시글 목록 페이지로 이동하는 메서드
    @GetMapping("pList")
    public String pList() {
        return "service"; // "service"라는 뷰 페이지로 이동
    }

    // 게시글 작성 메서드
    @PostMapping("/pWrite")
    public ModelAndView pWrite(@ModelAttribute BoardDTO board) {
        System.out.println("\n게시글 작성 메소드\n[1]html → controller : " + board); // 게시글 작성 메소드 호출 시 디버깅용 로그 출력
        ModelAndView mav = bsvc.pWrite(board); // 서비스에서 작성 메소드 호출
        System.out.println(mav); // mav 객체 디버깅용 출력
        return mav; // 반환된 ModelAndView 객체 반환
    }

    // 게시글 상세보기 메서드
    @GetMapping("/pView/{pNum}")
    public ModelAndView pView(@PathVariable int pNum) {
        System.out.println("\n게시글 작성 메소드\n[1]html → controller : " + pNum); // 게시글 번호로 조회
        return bsvc.pView(pNum); // 서비스에서 게시글 조회 메소드 호출
    }

    // 게시글 수정 메서드
    @PostMapping("/pModify")
    public ModelAndView pModify(@ModelAttribute BoardDTO board) {
        System.out.println("\n게시글 수정 메소드\n[1]html → controller : " + board); // 게시글 수정 메소드 호출 시 디버깅용 로그 출력
        return bsvc.pModify(board); // 서비스에서 수정 메소드 호출
    }

    // 게시글 삭제 메서드
    @GetMapping("/pDelete")
    public ModelAndView pDelete(@ModelAttribute BoardDTO board) {
        System.out.println("\n게시글 삭제 메소드\n[1]html → controller : " + board); // 게시글 삭제 메소드 호출 시 디버깅용 로그 출력
        return bsvc.pDelete(board); // 서비스에서 삭제 메소드 호출
    }

    // 게시글 작성 페이지로 이동하는 메서드 (카테고리별)
    @GetMapping("/wEducation")
    public String wEducation() {
        return "education"; // "education" 뷰 페이지로 이동
    }

    @GetMapping("/wEntertainment")
    public String wEntertainment() {
        return "entertainment"; // "entertainment" 뷰 페이지로 이동
    }

    @GetMapping("/wLife")
    public String wLife() {
        return "life"; // "life" 뷰 페이지로 이동
    }

    @GetMapping("/wEconomy")
    public String wEconomy() {
        return "economy"; // "economy" 뷰 페이지로 이동
    }

    @GetMapping("/wSport")
    public String wSport() {
        return "sport"; // "sport" 뷰 페이지로 이동
    }

    @GetMapping("/wShopping")
    public String wShopping() {
        return "shopping"; // "shopping" 뷰 페이지로 이동
    }

    @GetMapping("/wGame")
    public String wGame() {
        return "game"; // "game" 뷰 페이지로 이동
    }

    @GetMapping("/wTravel")
    public String wTravel() {
        return "travel"; // "travel" 뷰 페이지로 이동
    }

    @GetMapping("/wQuestion")
    public String wQuestion() {
        return "question"; // "question" 뷰 페이지로 이동
    }

    @GetMapping("/wComputer")
    public String wComputer() {
        return "computer"; // "computer" 뷰 페이지로 이동
    }

    @GetMapping("/wHealth")
    public String wHealth() {
        return "health"; // "health" 뷰 페이지로 이동
    }

    @GetMapping("/wPolitic")
    public String wPolitic() {
        return "politic"; // "politic" 뷰 페이지로 이동
    }

}
