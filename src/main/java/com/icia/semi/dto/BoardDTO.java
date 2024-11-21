package com.icia.semi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Clob;
import java.time.LocalDateTime;

@Data
public class BoardDTO {

    private int PNum;           //게시글 ID (기본키)
    private String PTitle;       //게시글 제목
    private String PContent;     //게시글 내용
    private String SId;          //작성자 ID (MEMBERS 테이블과 연결)
    private int CNum;            //카테고리 번호 (CATEGORIES 테이블과 연결)
    private int SRecomm;         //추천수 (기본값 0)
    private int SViews;           //조회수 (기본값 0)
    private String Status;       //상태(해결완료, 해결중, 차단)
    private LocalDateTime Create_At;  //작성일
    private LocalDateTime Modify_At;  //수정일
    private MultipartFile BFile;
    private String BFileName;



    public static BoardDTO toDTO(BoardEntity entity) {
        BoardDTO dto = new BoardDTO();

        dto.setPNum(entity.getPNum());
        dto.setPTitle(entity.getPTitle());
        dto.setPContent(entity.getPContent());
        dto.setSId(entity.getMember().getSId());
        dto.setCNum(entity.getCNum());
        dto.setSRecomm(entity.getSRecomm());
        dto.setSViews(entity.getSViews());
        dto.setStatus(entity.getStatus());
        dto.setCreate_At(entity.getCreate_At());
        dto.setModify_At(entity.getModify_At());
        dto.setBFileName(entity.getBFileName());


        return dto;
    }



}
