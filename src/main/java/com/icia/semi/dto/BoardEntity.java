package com.icia.semi.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Clob;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "POST")
@SequenceGenerator(name = "POST_SEQ_GENERATOR", sequenceName = "POST_SEQ", allocationSize = 1)
public class BoardEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ_GENERATOR")
    private int PNum;

    @Column
    private String PTitle;

    @Column(length = 1000)
    private String PContent;

    @Column
    private int CNum;

    @Column
    private int SRecomm;

    @Column
    private int SViews;

    @Column
    private String Status;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime Create_At;

    @Column(insertable = false)
    @CreationTimestamp
    private LocalDateTime Modify_At;

//    @Column
//    private String SID;

    @ManyToOne
    @JoinColumn(name = "SId")
    private MemberEntity member;

    @Column
    private String BFileName;



    public static BoardEntity toEntity(BoardDTO dto) {
        BoardEntity entity = new BoardEntity();
        MemberEntity member = new MemberEntity();

        entity.setPNum(dto.getPNum());
        entity.setPTitle(dto.getPTitle());
        entity.setPContent(dto.getPContent());
        // entity.setSID(dto.getSID());
        entity.setCNum(dto.getCNum());
        entity.setSRecomm(dto.getSRecomm());
        entity.setSViews(dto.getSViews());
        entity.setStatus(dto.getStatus());
        entity.setCreate_At(dto.getCreate_At());
        entity.setModify_At(dto.getModify_At());

        member.setSId(dto.getSId());
        entity.setMember(member);

        entity.setBFileName(dto.getBFileName());


        return entity;
    }



}
