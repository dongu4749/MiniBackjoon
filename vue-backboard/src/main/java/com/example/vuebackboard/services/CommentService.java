package com.example.vuebackboard.services;

import com.example.vuebackboard.entity.CommentEntity;
import com.example.vuebackboard.entity.CommentRepository;
import com.example.vuebackboard.entity.CommentRepositoryCustom;
import com.example.vuebackboard.model.Header;
import com.example.vuebackboard.model.Pagination;
import com.example.vuebackboard.model.SearchCondition;
import com.example.vuebackboard.web.dtos.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final  CommentRepository commentRepository;
    private final CommentRepositoryCustom commentRepositoryCustom;

    public Header<List<CommentDto>> getCommentList(Pageable pageable, SearchCondition searchCondition){
        List<CommentDto> dtos = new ArrayList<>();

        Page<CommentEntity> commentEntities = commentRepositoryCustom.findAllBySearchCondition(pageable, searchCondition);
        for (CommentEntity entity : commentEntities){
            CommentDto dto = CommentDto.builder()
                    .idx(entity.getIdx())
                    .board(entity.getBoard())
                    .id(entity.getId())
                    .comment(entity.getComment())
                    .build();

            dtos.add(dto);
        }

        Pagination pagination = new Pagination(
                (int) commentEntities.getTotalElements()
                , pageable.getPageNumber() + 1
                , pageable.getPageSize()
                ,10
        );

        return Header.OK(dtos, pagination);
    }

    public CommentDto getComment(Long id){
        CommentEntity entity = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return CommentDto.builder()
                .idx(entity.getIdx())
                .board(entity.getBoard())
                .id(entity.getId())
                .comment(entity.getComment())
                .build();
    }

    public CommentEntity addComment(CommentDto commentDto){
        CommentEntity entity = CommentEntity.builder()
                .board(commentDto.getBoard())
                .id(commentDto.getId())
                .comment(commentDto.getComment())
                .build();
        return  commentRepository.save(entity);
    }

    public void delete(Long id){
        CommentEntity entity = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        commentRepository.delete(entity);
    }
}
