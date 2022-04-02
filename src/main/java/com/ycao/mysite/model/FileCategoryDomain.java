package com.ycao.mysite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileCategoryDomain implements Serializable {

    private Integer cid;
    private String cname;
    private String creationTime;
    private Integer authorId;
    private String ts;
    private String dr;

    public FileCategoryDomain(String fileCategory, Integer userId) {
        this.cname=fileCategory;
        this.authorId=userId;
    }
}
