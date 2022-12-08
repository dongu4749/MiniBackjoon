package com.example.vuebackboard.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckAnswerDto implements Serializable {
    private Long idx;
    private String problem;
    private String id;
    private String code;
    private String result;
    private String createdAt;
    private String problemid;
}