package com.icia.semi.dto;

import lombok.Data;

import java.sql.Clob;
import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private int CNum;   // 댓글 번호
    private int PNum;   // 게시글 ID (answer에서 가져옴)
    //private int ANum;   // 답변 번호 (답변에 댓글을 달려고 외래키로 가져온듯)
    private String SId; // 댓글 작성자 ID (members에서 가져옴)
    private Clob Content;   // 댓글 내용 (텍스트가 길어서 Clob 사용)
    private int Parent_Id;  // 부모 댓글 번호 (댓글에 대한 답글이면 부모 댓글의 CNUM이 저장됨)
    private LocalDateTime Created_At;   // 댓글 작성일
    private LocalDateTime Modify_At;    // 댓글 수정일

    public static CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();

        dto.setCNum(entity.getCNum());
        dto.setPNum(entity.getBoard().getPNum());
        //dto.setANum(entity.getANum());
        dto.setSId(entity.getMember().getSId());
        dto.setContent(entity.getContent());
        dto.setParent_Id(entity.getParent_Id());
        dto.setCreated_At(entity.getCreated_At());
        dto.setModify_At(entity.getModify_At());

        return dto;
    }
}


