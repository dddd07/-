package com.icia.semi.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "MEMBERS")
public class MemberEntity {

    @Id
    @Column(name = "SId", length = 100, nullable = false) // SID는 기본키
    private String SId;  // 유저 ID

    @Column(name = "SPw", length = 100, nullable = false) // 비밀번호
    private String SPw;  // 유저 비밀번호

    @Column(name = "SName", length = 100, nullable = false) // 유저 이름
    private String SName;  // 유저 이름

    @Column(name = "SBirth") // 생년월일
    private LocalDate SBirth;  // 유저 생년월일

    @Column(name = "SGender", length = 100)  // 성별
    private String SGender;  // 유저 성별

    @Column(name = "SEmail", length = 100, nullable = false, unique = true) // 이메일
    private String SEmail;  // 유저 이메일

    @Column(name = "SPhone", length = 100)  // 전화번호
    private String SPhone;  // 유저 전화번호

    @Column(name = "Accepted_Count", nullable = false, columnDefinition = "NUMBER DEFAULT 0")  // 채택된 수
    private int Accepted_Count = 0;  // 채택된 수 (기본값 0)

    @Column(name = "Is_Admin", nullable = false, columnDefinition = "NUMBER(1) DEFAULT 0") // 어드민 여부
    private int Is_Admin = 0;  // 어드민 여부 (0 = 일반 유저, 1 = 어드민)

    @OneToMany(mappedBy = "member")
    private List<BoardEntity> boards;

    // DTO → Entity 변환 메소드
    public static MemberEntity toEntity(MemberDTO dto) {
        MemberEntity entity = new MemberEntity();

        entity.setSId(dto.getSId());
        entity.setSPw(dto.getSPw());
        entity.setSName(dto.getSName());

        // LocalDate 설정
        entity.setSBirth(dto.getSBirth());

        entity.setSGender(dto.getSGender());
        entity.setSEmail(dto.getSEmail());
        entity.setSPhone(dto.getSPhone());
        return entity;
    }

    // *** (변경 사항) 관리자 여부 확인 메서드 추가 ***
    // 이 메서드는 Entity 내부에서 해당 사용자가 관리자 여부를 반환하는 메서드입니다.
//    public boolean isAdmin() {
//        return this.Is_Admin == 1; // 1이면 관리자로 간주
//    }

}


