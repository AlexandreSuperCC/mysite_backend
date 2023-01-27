package com.ycao.mysite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDomain {
    private int id;
    private String component;
    private String content;
    private int userId;
    private String ts;
}
