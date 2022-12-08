package com.example.vuebackboard.web;

import com.example.vuebackboard.entity.CheckAnswerEntity;
import com.example.vuebackboard.model.Header;
import com.example.vuebackboard.model.SearchCondition;
import com.example.vuebackboard.services.CheckAnswerService;
import com.example.vuebackboard.web.dtos.CheckAnswerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class CheckAnswerController {
    private final CheckAnswerService checkAnswerService;

    @GetMapping("/problem/check")
    public Header<List<CheckAnswerDto>> checkAnswerList(
            @PageableDefault(sort = {"idx"}) Pageable pageable,
            SearchCondition searchCondition
    ){
        return checkAnswerService.getCheckAnswerList(pageable, searchCondition);
    }

    @PostMapping("/problem/check/{id}")
    public CheckAnswerEntity addResult(@RequestBody CheckAnswerDto checkAnswerDto){
        return checkAnswerService.addResult(checkAnswerDto);
    }

    @GetMapping("/problem/check/{id}")
    public CheckAnswerDto getCheckAnswer(@PathVariable Long id){
        return checkAnswerService.getCheckAnswer(id);
    }
}
