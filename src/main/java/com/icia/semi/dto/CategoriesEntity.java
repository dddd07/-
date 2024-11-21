package com.icia.semi.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CATEGORIES")
@SequenceGenerator(name = "CA_SEQ_GENERATOR", sequenceName = "CA_SEQ", allocationSize = 1)
public class CategoriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORIES_SEQ_GENERATOR")
    private int CNUM;

    @Column
    private String CTITLE;

    public static CategoriesEntity toEntity(CategoriesDTO dto) {
        CategoriesEntity entity = new CategoriesEntity();

        entity.setCNUM(dto.getCNUM());
        entity.setCTITLE(dto.getCTITLE());

        return entity;
    }


}