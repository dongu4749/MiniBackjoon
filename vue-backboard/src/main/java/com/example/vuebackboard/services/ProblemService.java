package com.example.vuebackboard.services;

import com.example.vuebackboard.entity.*;
import com.example.vuebackboard.model.Header;
import com.example.vuebackboard.model.Pagination;
import com.example.vuebackboard.model.SearchCondition;
import com.example.vuebackboard.web.dtos.ProblemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.parsing.Problem;
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
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemRepositoryCustom problemRepositoryCustom;

    /**
     * 게시글 목록 가져오기
     *  @return
     */
    public Header<List<ProblemDto>> getBoardList(Pageable pageable, SearchCondition searchCondition) {
        List<ProblemDto> dtos = new ArrayList<>();

        Page<ProblemEntity> problemEntities = problemRepositoryCustom.findAllBySearchCondition(pageable, searchCondition);
        for (ProblemEntity entity : problemEntities) {
            ProblemDto dto = ProblemDto.builder()
                    .idx(entity.getIdx())
                    .author(entity.getAuthor())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .problem_answer(entity.getProblem_answer())
                    .build();

            dtos.add(dto);
        }

        Pagination pagination = new Pagination(
                (int) problemEntities.getTotalElements()
                , pageable.getPageNumber() + 1
                , pageable.getPageSize()
                , 10
        );

        return Header.OK(dtos, pagination);
    }

    /**
     * 게시글 가져오기
     */
    public ProblemDto getBoard(Long id) {
        ProblemEntity entity = problemRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return ProblemDto.builder()
                .idx(entity.getIdx())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .author(entity.getAuthor())
                .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                .problem_answer(entity.getProblem_answer())
                .build();
    }

    /**
     * 게시글 등록
     */
    public ProblemEntity create(ProblemDto problemDto) {
        ProblemEntity entity = ProblemEntity.builder()
                .title(problemDto.getTitle())
                .contents(problemDto.getContents())
                .author(problemDto.getAuthor())
                .createdAt(LocalDateTime.now())
                .problem_answer(problemDto.getProblem_answer())
                .build();
        return problemRepository.save(entity);
    }

    /**
     * 게시글 수정
     */
    public ProblemEntity update(ProblemDto problemDto) {
        ProblemEntity entity = problemRepository.findById(problemDto.getIdx()).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        entity.setTitle(problemDto.getTitle());
        entity.setContents(problemDto.getContents());
        return problemRepository.save(entity);
    }

    /**
     * 게시글 삭제
     */
    public void delete(Long id) {
        ProblemEntity entity = problemRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        problemRepository.delete(entity);
    }
}
