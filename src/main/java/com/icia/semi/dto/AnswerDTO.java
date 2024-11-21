package com.icia.semi.dto;

import lombok.Data;

import java.sql.Clob;
import java.time.LocalDateTime;

@Data
public class AnswerDTO {

    private int ANum;   // 답변 번호
    private int PNum;   // 게시글 번호
    private String SId; // 댓글 작성자 ID(members에서 가져옴)
    private Clob Content;
    private String Adopt;
    private int Arecomm;
    private LocalDateTime Created_At;   // 답변 작성일
    private LocalDateTime Modify_At;    // 답변 수정일

    public static AnswerDTO toDTO(AnswerEntity entity) {
        AnswerDTO dto = new AnswerDTO();

        dto.setANum(entity.getANum());
        dto.setPNum(entity.getBoard().getPNum());
        dto.setSId(entity.getMember().getSId());
        dto.setContent(entity.getContent());
        dto.setAdopt(entity.getAdopt());
        dto.setArecomm(entity.getArecomm());
        dto.setCreated_At(entity.getCreated_At());
        dto.setModify_At(entity.getModify_At());


        return dto;
    }
}
