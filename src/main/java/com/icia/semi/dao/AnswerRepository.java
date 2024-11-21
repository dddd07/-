package com.icia.semi.dao;

import com.icia.semi.dto.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Integer> {
    List<AnswerEntity> findByMember_SIdOrderByANumAsc(String sId);
}
