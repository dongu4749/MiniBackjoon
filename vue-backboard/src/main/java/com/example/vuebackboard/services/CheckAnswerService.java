package com.example.vuebackboard.services;

import com.example.vuebackboard.entity.*;
import com.example.vuebackboard.model.Header;
import com.example.vuebackboard.model.Pagination;
import com.example.vuebackboard.model.SearchCondition;
import com.example.vuebackboard.web.dtos.CheckAnswerDto;
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
public class CheckAnswerService {
    private final CheckAnswerRepository checkAnswerRepository;
    private final CheckAnswerRepositoryCustom checkAnswerRepositoryCustom;

    public Header<List<CheckAnswerDto>> getCheckAnswerList(Pageable pageable, SearchCondition searchCondition){
        List<CheckAnswerDto> dtos = new ArrayList<>();

        Page<CheckAnswerEntity> checkAnswerEntities = checkAnswerRepositoryCustom.findAllBySearchCondition(pageable, searchCondition);
        for(CheckAnswerEntity entity : checkAnswerEntities){
            CheckAnswerDto dto = CheckAnswerDto.builder()
                    .idx(entity.getIdx())
                    .problem(entity.getProblem())
                    .id(entity.getId())
                    .code(entity.getCode())
                    .result(entity.getResult())
                    .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .build();

            dtos.add(dto);
        }

        Pagination pagination = new Pagination(
                (int) checkAnswerEntities.getTotalElements()
                , pageable.getPageNumber() + 1
                , pageable.getPageSize()
                , 10
        );

        return Header.OK(dtos, pagination);
    }

    public CheckAnswerEntity addResult(CheckAnswerDto checkAnswerDto){
        CheckAnswerEntity entity = CheckAnswerEntity.builder()
                .problem(checkAnswerDto.getProblem())
                .id(checkAnswerDto.getId())
                .code(checkAnswerDto.getCode())
                .result(checkAnswerDto.getResult())
                .createdAt(LocalDateTime.now())
                .problemid(checkAnswerDto.getProblemid())
                .build();
        return  checkAnswerRepository.save(entity);
    }

    public CheckAnswerDto getCheckAnswer(Long id){
        CheckAnswerEntity entity = checkAnswerRepository.findById(id).orElseThrow(() -> new RuntimeException("결과를 찾을 수 없습니다."));
        return CheckAnswerDto.builder()
                .idx(entity.getIdx())
                .problem(entity.getProblem())
                .id(entity.getId())
                .code(entity.getCode())
                .result(entity.getResult())
                .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                .build();
    }
}
