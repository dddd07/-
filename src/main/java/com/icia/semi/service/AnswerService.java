package com.icia.semi.service;

import com.icia.semi.dao.AnswerRepository;
import com.icia.semi.dto.AnswerDTO;
import com.icia.semi.dto.AnswerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository arepo;


    public List<AnswerDTO> mAnswerList(AnswerDTO answer) {
        System.out.println("[2] 나의 답변 목록 불러오기 : " + answer);

        List<AnswerDTO> mAnswerList = new ArrayList<>(); // DTO 객체를 저장할 리스트 초기화

        // SId 기준으로 게시글 목록 가져오기
        List<AnswerEntity> entityList = arepo.findByMember_SIdOrderByANumAsc(answer.getSId());

        // DTO로 변환하여 목록에 추가
        for (AnswerEntity entity : entityList) {
            mAnswerList.add(AnswerDTO.toDTO(entity)); // 각 엔티티를 DTO로 변환 후 리스트에 추가
        }

        return mAnswerList;
    }
}
