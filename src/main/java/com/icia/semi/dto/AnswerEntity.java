package com.icia.semi.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Clob;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ANSWERENTITY")
@SequenceGenerator(name = "ANS_SEQ_GENERATOR", sequenceName = "ANS_SEQ", allocationSize = 1)
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANS_SEQ_GENERATOR") // 시퀀스를 사용하여 기본 키 생성
    private int ANum;  // 답변 번호

    @ManyToOne
    @JoinColumn(name = "PNUM", nullable = false)  // 게시글 번호 외래 키
    private BoardEntity board;

    @ManyToOne
    @JoinColumn(name = "SID", nullable = false)  // 답변 작성자 ID 외래 키
    private MemberEntity member;  // MemberEntity와 다대일 관계

    @Lob
    @Column(columnDefinition = "CLOB") // Clob을 사용하여 긴 답변 내용을 저장
    private Clob Content;  // 답변 내용

    @Column(nullable = false)
    private String Adopt;  // 답변 채택 여부 (채택된 답변은 "Y", 그렇지 않으면 "N" 등으로 저장)

    @Column(nullable = false)
    private int Arecomm;  // 답변 추천 수

    @CreationTimestamp
    @Column(name = "Created_At", updatable = false)  // 답변 작성일
    private LocalDateTime Created_At;  // 답변 작성일

    @UpdateTimestamp
    @Column(name = "Modify_At")  // 답변 수정일
    private LocalDateTime Modify_At;  // 답변 수정일

    // AnswerDTO 객체를 AnswerEntity로 변환하는 메소드
    public static AnswerEntity toEntity(AnswerDTO dto) {
        AnswerEntity entity = new AnswerEntity();
        BoardEntity board = new BoardEntity();
        MemberEntity member = new MemberEntity();
        entity.setANum(dto.getANum());

        board.setPNum(dto.getPNum());

        entity.setBoard(board);

        member.setSId(dto.getSId());
        entity.setMember(member);
        entity.setContent(dto.getContent());
        entity.setAdopt(dto.getAdopt());
        entity.setArecomm(dto.getArecomm());
        entity.setCreated_At(dto.getCreated_At());
        entity.setModify_At(dto.getModify_At());

        // 게시글 및 작성자와의 연관 관계 설정

        board.setPNum(dto.getPNum());  // DTO에서 받은 게시글 번호로 설정
        entity.setBoard(board);


        return entity;
    }


}
