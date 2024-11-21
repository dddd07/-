package com.icia.semi.dao;

import com.icia.semi.dto.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    Optional<MemberEntity> findBySId(String SID);

}


