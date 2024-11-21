package com.icia.semi.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Clob;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "COMMENTENTITY")
@SequenceGenerator(name = "CMT_SEQ_GENERATOR", sequenceName = "CMT_SEQ", allocationSize = 1)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CMT_SEQ_GENERATOR") // 시퀀스를 사용하여 기본 키 생성
    private int CNum;   // 댓글 번호

    @ManyToOne
    @JoinColumn(name = "PNum", nullable = false) // 게시글 ID 외래 키
    private BoardEntity board;

    @ManyToOne
    @JoinColumn(name = "ANum") // 답변 번호, 자기 자신과의 관계 설정
    private CommentEntity parentComment;   // 자기 자신과의 다대일 관계

    @ManyToOne
    @JoinColumn(name = "SId", nullable = false) // 댓글 작성자 ID 외래 키
    private MemberEntity member;  // MemberEntity와 다대일 관계

    @Lob
    @Column(columnDefinition = "CLOB") // Clob을 사용하여 긴 댓글 내용을 저장
    private Clob Content;   // 댓글 내용

    @Column(name = "Parent_Id", nullable = true) // 부모 댓글 ID
    private int Parent_Id;  // 부모 댓글 번호 (답글이면 부모 댓글의 CNUM 저장)

    @CreationTimestamp
    @Column(name = "Created_At", updatable = false) // 댓글 작성일
    private LocalDateTime Created_At;  // 댓글 작성일

    @UpdateTimestamp
    @Column(name = "Modify_At") // 댓글 수정일
    private LocalDateTime Modify_At;  // 댓글 수정일

    // CommentDTO 객체를 CommentEntity로 변환하는 메소드
    public static CommentEntity toEntity(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        MemberEntity member = new MemberEntity();
        BoardEntity board = new BoardEntity();
        // AnswerEntity answer = new AnswerEntity(); - 2024-11-13 저녁 (이거 필요한 이유를 모르겠음.)

        board.setPNum(dto.getPNum());
        entity.setCNum(dto.getCNum());


        //entity.set(answer);
        //post.setANUM(dto.getANum());

        member.setSId(dto.getSId());
        entity.setContent(dto.getContent());
        entity.setParent_Id(dto.getParent_Id());
        entity.setCreated_At(dto.getCreated_At());
        entity.setModify_At(dto.getModify_At());


        // 답글일 경우 부모 댓글 설정
        if (dto.getParent_Id() != 0) {
            CommentEntity parentComment = new CommentEntity();
            parentComment.setCNum(dto.getParent_Id());
            entity.setParentComment(parentComment);
        }

        return entity;
    }

}
