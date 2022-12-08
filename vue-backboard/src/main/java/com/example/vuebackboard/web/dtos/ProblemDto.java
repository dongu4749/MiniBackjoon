package com.example.vuebackboard.web.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemDto implements Serializable {
    private Long idx;
    private String title;
    private String contents;
    private String author;
    private String problem_answer;
    private String createdAt;


}