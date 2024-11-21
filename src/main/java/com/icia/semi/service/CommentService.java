package com.icia.semi.service;

import com.icia.semi.dao.CommentRepository;
import com.icia.semi.dto.CommentDTO;
import com.icia.semi.dto.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository crepo;

    public List<CommentDTO> cList(int CNum) {
        List<CommentDTO> dtoList = new ArrayList<>();
        List<CommentEntity> entityList = crepo.findAll();

        for(CommentEntity entity : entityList ){
            dtoList.add(CommentDTO.toDTO(entity));
        }

        return dtoList;
    }

    public List<CommentDTO> SID(CommentDTO comment) {

        // 댓글 입력
        CommentEntity entity = CommentEntity.toEntity(comment);
        crepo.save(entity);

        // 댓글 입력 후 목록 불러오기
        List<CommentDTO> dtoList = cList(comment.getPNum());
        return dtoList;

    }


    public List<CommentDTO> cDelete(CommentDTO comment) {

        // 댓글 삭제
        crepo.deleteById(comment.getCNum());

        // 댓글 입력 후 목록 불러오기
        List<CommentDTO> dtoList = cList(comment.getPNum());
        return dtoList;

    }
}
