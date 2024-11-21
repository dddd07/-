package com.icia.semi.dao;

import com.icia.semi.dto.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {


    List<BoardEntity> findAllByOrderByPNumDesc();

    List<BoardEntity> findByMember_SIdContainingOrderByPNumDesc(String keyword);

    List<BoardEntity> findByPTitleContainingOrderByPNumDesc(String keyword);

    List<BoardEntity> findByPContentContainingOrderByPNumDesc(String keyword);

    List<BoardEntity> findByMember_SIdOrderByPNumAsc(String sId);
}