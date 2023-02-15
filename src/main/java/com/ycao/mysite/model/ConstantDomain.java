package com.ycao.mysite.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "constant form")
public class ConstantDomain {
    private Integer id;
    private String code;
    private String name;
    private String creationTime;
    private String creatorId;
    private String content;
    private String domain;
}
