package com.icia.semi.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class MemberDTO {

    private String SId;         // 유저 아이디
    private String SPw;         // 유저 비밀번호
    private String SName;       // 유저 이름
    private LocalDate SBirth;   // 유저 생년월일
    private String SGender;     // 유저 성별
    private String SEmail;      // 유저 이메일
    private String SPhone;      // 유저 전화번호
    private int Accepted_Count; // 채택된 수 (기본값 0)
    private int Is_Admin;       // 어드민 여부 (기본값 0, 0 = 일반 유저, 1 = 어드민)

    public static MemberDTO toDTO(MemberEntity entity) {
        MemberDTO dto = new MemberDTO();
        dto.setSId(entity.getSId());
        dto.setSPw(entity.getSPw());
        dto.setSName(entity.getSName());

        // Date → LocalDate 변환
        if (entity.getSBirth() != null) {
            dto.setSBirth(entity.getSBirth());
        }

        dto.setSGender(entity.getSGender());
        dto.setSEmail(entity.getSEmail());
        dto.setSPhone(entity.getSPhone());
        dto.setIs_Admin(entity.getIs_Admin());
        return dto;
    }
}


