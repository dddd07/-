package com.icia.semi.dto;

import lombok.Data;

@Data
public class CategoriesDTO {

    private int CNUM;       //카테고리 번호
    private String CTITLE;      //카테고리 이름

    public static CategoriesDTO toDTO(CategoriesEntity entity) {
        CategoriesDTO dto = new CategoriesDTO();

        dto.setCNUM(entity.getCNUM());
        dto.setCTITLE(entity.getCTITLE());

        return dto;
    }


}
