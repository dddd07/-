package com.icia.semi.service;

import com.icia.semi.dao.BoardRepository;
import com.icia.semi.dao.MemberRepository;
import com.icia.semi.dto.BoardDTO;
import com.icia.semi.dto.MemberDTO;
import com.icia.semi.dto.MemberEntity;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository mrepo;

    private final BoardRepository brepo;

    // 메일 인증
    private final JavaMailSender mailSender;

    // 저장 경로
    Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");

    // 암호화
    private BCryptPasswordEncoder pwEnc = new BCryptPasswordEncoder();

    // 로그인
    private final HttpSession session;

    private ModelAndView mav;

    public String idCheck(String SId) {
        String result = "";
        Optional<MemberEntity> entity = mrepo.findById(SId);

        if (entity.isPresent()) {
            result = "NO";
        } else {
            result = "OK";
        }

        return result;
    }

    public String emailCheck(String sEmail) {
        String uuid = null;

        // 인증번호
        uuid = UUID.randomUUID().toString().substring(0, 8);

        // 이메일 발송
        MimeMessage mail = mailSender.createMimeMessage();

        String message = "<h2>안녕하세요. 다다답 가입을 위한 이메일 확인 절차입니다.</h2>"
                + "<p>인증번호는 <b>" + uuid + "</b> 입니다.</p>";

        try {
            // 이메일 전송 준비
            mail.setSubject("다다답 인증번호");
            mail.setText(message, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(sEmail));

            // 이메일 전송
            //mailSender.send(mail);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return uuid;
    }


    public ModelAndView mJoin(MemberDTO member) {
        System.out.println("[2] controller → service : " + member);
        mav = new ModelAndView();

        // (1) 비밀번호 암호화
        member.setSPw(pwEnc.encode(member.getSPw()));

        System.out.println("암호화 이후 : " + member);

        // (2) dto(MemberDTO) → entity(MemberEntity)
        MemberEntity entity = MemberEntity.toEntity(member);

        // (3) db저장
        try {
            mrepo.save(entity);
            mav.setViewName("redirect:/mLoginForm");
        } catch (Exception e) {
            mav.setViewName("redirect:/mJoinForm");
            throw new RuntimeException(e);
        }

        return mav;
    }


    public ModelAndView mLogin(MemberDTO member) {
        System.out.println("로그인 확인 서비스 : " + member);

        ModelAndView mav = new ModelAndView();

        // (1) 아이디가 존재하는지 확인
        Optional<MemberEntity> entity = mrepo.findById(member.getSId()); // 아이디로 DB 조회
        if (entity.isPresent()) {
            // (2) 해당 아이디의 암호화된 비밀번호와 로그인 페이지에서 입력한 비밀번호가 일치하는지 확인
            // DB에 저장된 비밀번호 : entity.get().getMPw()
            // 로그인 창에서 입력한 비밀번호 : member.getMPw()
            if (pwEnc.matches(member.getSPw(), entity.get().getSPw())) {
                // (3) 비밀번호 일치 시, entity → dto 변환
                MemberDTO login = MemberDTO.toDTO(entity.get());


                System.out.println("login : " + login);

                // 세션에 로그인 정보 저장
                session.setAttribute("loginId", login.getSId());
                session.setAttribute("Is_Admin", login.getIs_Admin());
                System.out.println("성공했을시 세션 확인 " + login.getSId());

                // 로그인 성공 후 메인 페이지로 리다이렉트
                mav.setViewName("redirect:/index");
            } else {
                // 비밀번호 불일치 시
                System.out.println("비밀번호가 일치하지 않습니다.");
                mav.setViewName("redirect:/login"); // 비밀번호 오류 페이지로 리다이렉트
            }
        } else {
            // 아이디가 존재하지 않는 경우
            System.out.println("아이디 존재하지 않습니다.");
            mav.setViewName("redirect:/login"); // 아이디 오류 페이지로 리다이렉트
        }

        return mav;
    }

    // 로그인한 사용자 정보로 마이페이지 데이터 처리
//    public ModelAndView getMyProfile(String loginId) {
//        System.out.println("[2] 마이프로필 : " + loginId);

//        ModelAndView mav = new ModelAndView();
//
//        // DB에서 로그인된 사용자의 정보 조회
//        Optional<MemberEntity> memberEntity = mrepo.findById(loginId);
//
//        if (memberEntity.isPresent()) {
//            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity.get());  // Entity -> DTO로 변환
//            mav.addObject("member", memberDTO);  // 마이페이지에 사용자 정보 전달
//        } else {
//            // 사용자 정보가 없으면, 예외 처리 (사용자 없는 경우)
//            mav.addObject("error", "사용자 정보를 찾을 수 없습니다.");
//        }
//
//        mav.setViewName("myProfile");  // 마이페이지 뷰로 이동
//        return mav;
//  }

    // 마이프로필 페이지
    public ModelAndView myProfile(String SId) {
        System.out.println("[2] 마이프로필 페이지 : " + SId);

        ModelAndView mav = new ModelAndView();

        // SID로 DB에서 사용자 조회
        Optional<MemberEntity> entity = mrepo.findBySId(SId);  // SID로 회원 정보 조회

        if (entity.isPresent()) {
            MemberDTO dto = MemberDTO.toDTO(entity.get());  // DB에서 조회된 정보를 DTO로 변환
            mav.addObject("member", dto);  // mav에 DTO 추가
            mav.setViewName("myProfile");  // 마이프로필 페이지로 이동
        } else {
            mav.setViewName("redirect:/index");  // 회원이 없으면 홈으로 리다이렉트
        }

        return mav;
    }

    // 내 정보 수정
    public ModelAndView modify(MemberDTO member) {
        System.out.println("[2] 수정된 정보 : " + member);
        mav = new ModelAndView();

        // (2) 비밀번호 암호화
        member.setSPw(pwEnc.encode(member.getSPw()));

        System.out.println("암호화 이후 : " + member);

        // (3) dto(MemberDTO) → entity(MemberEntity)
        MemberEntity entity = MemberEntity.toEntity(member);

        // (4) db저장
        try {
            mrepo.save(entity);
            mav.addObject("member",member);     // 앞으로 안돌때 이거 쓰자..
            mav.setViewName("redirect:/myProfile");
        } catch (Exception e) {
            mav.setViewName("redirect:/index");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return mav;
    }

    // 회원탈퇴
    public ModelAndView mDelete(MemberDTO member) {
        System.out.println("[2] Controller → service : " + member);
        mav = new ModelAndView();

        mrepo.deleteById(member.getSId());
        session.invalidate();

        mav.setViewName("redirect:/index");

        return mav;
    }

}