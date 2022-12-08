package com.example.vuebackboard.web;

import com.example.vuebackboard.entity.ProblemEntity;
import com.example.vuebackboard.model.Header;
import com.example.vuebackboard.model.SearchCondition;
import com.example.vuebackboard.services.ProblemService;
import com.example.vuebackboard.web.dtos.ProblemDto;
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
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping("/problem/list")
    public Header<List<ProblemDto>> problemList(
            @PageableDefault(sort = {"idx"}) Pageable pageable,
            SearchCondition searchCondition
    ) {
        return problemService.getBoardList(pageable,searchCondition);
    }

    @GetMapping("/problem/{id}")
    public ProblemDto getBoard(@PathVariable Long id) {
        return problemService.getBoard(id);
    }

    @PostMapping("/problem")
    public ProblemEntity create(@RequestBody ProblemDto problemDto) {
        return problemService.create(problemDto);
    }

    @PatchMapping("/problem")
    public ProblemEntity update(@RequestBody ProblemDto problemDto) {
        return problemService.update(problemDto);
    }

    @DeleteMapping("/problem/{id}")
    public void delete(@PathVariable Long id) {
        problemService.delete(id);
    }
}
