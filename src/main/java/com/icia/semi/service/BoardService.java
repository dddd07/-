package com.icia.semi.service;

import com.icia.semi.dao.BoardRepository;
import com.icia.semi.dto.BoardDTO;
import com.icia.semi.dto.BoardEntity;
import com.icia.semi.dto.SearchDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // Spring의 서비스 레이어를 나타내는 애너테이션
@RequiredArgsConstructor // Lombok을 사용하여 final 필드에 대한 생성자를 자동으로 생성
public class BoardService {

    private ModelAndView mav; // 모델과 뷰를 관리하는 객체
    private final BoardRepository brepo; // 게시판 관련 데이터 접근 객체


    // 파일 저장 경로 (프로젝트 내 static/upload 폴더)
    Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/upload");

    // 게시글 작성 메서드
    public ModelAndView pWrite(BoardDTO board) {
        System.out.println("[2] controller → service : " + board); // 디버깅용 로그 출력
        mav = new ModelAndView(); // 새로운 ModelAndView 객체 생성

        // 게시글에 첨부된 파일 가져오기
        MultipartFile bFile = board.getBFile();
        String savePath = "";

        // 파일이 비어 있지 않으면 파일 처리
        if(!bFile.isEmpty()){
            // 파일 이름에 UUID를 추가하여 중복을 방지
            String uuid = UUID.randomUUID().toString().substring(0,8);
            String fileName = bFile.getOriginalFilename();
            String bFileName = uuid + "_" + fileName;

            // 파일명 설정
            board.setBFileName(bFileName);

            // 파일 저장 경로 설정
            savePath = path + "\\" + bFileName;
        } else {
            // 파일이 없으면 기본 이미지 파일 설정
            board.setBFileName("default.jpg");
        }

        try {
            // BoardDTO를 BoardEntity로 변환 후 DB에 저장
            BoardEntity entity = BoardEntity.toEntity(board);
            brepo.save(entity);

            // 파일을 실제 서버에 저장
            if (!bFile.isEmpty()) {
                bFile.transferTo(new File(savePath));
            }
        } catch (Exception e) {
            System.out.println("게시글 등록 실패!");
        }

        // 게시글 작성 후 메인 페이지로 리다이렉트
        mav.setViewName("redirect:/index");

        return mav;
    }

    // 게시글 목록을 가져오는 메서드
    public List<BoardDTO> boardList() {
        List<BoardDTO> dtoList = new ArrayList<>(); // DTO 객체를 저장할 리스트 초기화

        // 게시글 엔티티 목록을 저장소에서 가져옴 (BNUM 기준 내림차순 정렬)
        List<BoardEntity> entityList = brepo.findAllByOrderByPNumDesc();

        // 엔티티를 DTO로 변환하여 목록에 추가
        for (BoardEntity entity : entityList) {
            dtoList.add(BoardDTO.toDTO(entity)); // 각 엔티티를 DTO로 변환 후 리스트에 추가
        }

        return dtoList; // DTO 리스트 반환
    }

    // 게시글 검색 결과를 반환하는 메서드
    public List<BoardDTO> searchList(SearchDTO search) {
        List<BoardDTO> dtoList = new ArrayList<>(); // 검색 결과를 담을 DTO 리스트 초기화
        List<BoardEntity> entityList = new ArrayList<>(); // 검색 결과 엔티티 리스트 초기화

        // 검색 카테고리에 따라 다른 필드에서 검색 수행
        if (search.getCategory().equals("SId")) {
            entityList = brepo.findByMember_SIdContainingOrderByPNumDesc(search.getKeyword());
            // 특정 키워드를 포함하는 SId 기준으로 검색하여 내림차순 정렬
        } else if (search.getCategory().equals("pTitle")) {
            entityList = brepo.findByPTitleContainingOrderByPNumDesc(search.getKeyword());
            // 특정 키워드를 포함하는 pTitle 기준으로 검색하여 내림차순 정렬
        } else if (search.getCategory().equals("pContent")) {
            entityList = brepo.findByPContentContainingOrderByPNumDesc(search.getKeyword());
            // 특정 키워드를 포함하는 pContent 기준으로 검색하여 내림차순 정렬
        }

        // 엔티티를 DTO로 변환하여 목록에 추가
        for (BoardEntity entity : entityList) {
            dtoList.add(BoardDTO.toDTO(entity)); // 엔티티를 DTO로 변환 후 리스트에 추가
        }

        return dtoList; // 검색된 DTO 리스트 반환
    }

    // 특정 게시글의 내용을 조회하는 메서드
    public ModelAndView pView(int PNum) {
        System.out.println("[2] controller → service : " + PNum); // 디버깅용 로그 출력
        mav = new ModelAndView(); // 새로운 ModelAndView 객체 생성

        // 게시글 번호로 엔티티를 검색
        Optional<BoardEntity> entity = brepo.findById(PNum);
        if (entity.isPresent()) { // 엔티티가 존재하는 경우
            BoardDTO dto = BoardDTO.toDTO(entity.get()); // 엔티티를 DTO로 변환
            mav.addObject("view", dto); // Model에 DTO 추가
            mav.setViewName("view"); // 뷰 이름 설정
        } else { // 엔티티가 존재하지 않는 경우
            mav.setViewName("redirect:/view"); // 다른 경로로 리다이렉트
        }

        return mav; // ModelAndView 객체 반환
    }

    // 게시글을 수정하는 메서드
    public ModelAndView pModify(BoardDTO board) {
        System.out.println("[2] controller → service : " + board); // 디버깅용 로그 출력
        mav = new ModelAndView(); // 새로운 ModelAndView 객체 생성

        return mav; // ModelAndView 객체 반환 (현재 미완성)
    }

    // 게시글을 삭제하는 메서드
    public ModelAndView pDelete(BoardDTO board) {
        System.out.println("[2] controller → service : " + board); // 디버깅용 로그 출력
        mav = new ModelAndView(); // 새로운 ModelAndView 객체 생성

        return mav; // ModelAndView 객체 반환 (현재 미완성)
    }

    // 나의 질문 게시글 불러오는 메소드
    public List<BoardDTO> mBoardList(BoardDTO board) {
        System.out.println("[2] 나의 질문 목록 불러오기 : " + board);

        List<BoardDTO> mBoardList = new ArrayList<>(); // DTO 객체를 저장할 리스트 초기화

        // SId 기준으로 게시글 목록 가져오기
        List<BoardEntity> entityList = brepo.findByMember_SIdOrderByPNumAsc(board.getSId());

        // DTO로 변환하여 목록에 추가
        for (BoardEntity entity : entityList) {
            mBoardList.add(BoardDTO.toDTO(entity)); // 각 엔티티를 DTO로 변환 후 리스트에 추가
        }

        return mBoardList;
    }
}
