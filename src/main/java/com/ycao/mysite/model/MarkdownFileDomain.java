package com.ycao.mysite.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
/**
 * md file model
 * Created by ycao on 2021-12-11 23:42:39
 */
public class MarkdownFileDomain implements Serializable {

    //the primary key for file
    private Integer mid;
    //the name of the file
    private String mname;
    //the time of the creation
    private String creationTime;
    //the id of the creator
    private Integer authorId;
    private String rate;
    private String content;
    private String htmlText;
    private String ts;
    private String dr;
    private String pkCategory;

    public MarkdownFileDomain(Integer mid,String mname, Integer authorId, String rate, String content, String pk_category,String htmlText) {
        this.mid = mid;
        this.mname = mname;
        this.authorId = authorId;
        this.rate = rate;
        this.content = content;
        this.pkCategory = pk_category;
        this.htmlText = htmlText;
    }

    public MarkdownFileDomain(Integer mid, String mname, String rate, String content, String pk_category,String htmlText) {
        this.mid = mid;
        this.mname = mname;
        this.rate = rate;
        this.content = content;
        this.pkCategory = pk_category;
        this.htmlText = htmlText;
    }
}
