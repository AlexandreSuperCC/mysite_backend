package com.ycao.mysite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * upload file related
 * Created by ycao on 20211106
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class AttAchDomain implements Serializable {

    //the primary key for file
    private Integer id;
    //the name of the file
    private String fname;
    //the type of the file
    private String ftype;
    //the address of the file
    private String fkey;
    //the id of the creator
    private Integer authorid;
    //the time of the creation
    private String created;
    private String ts;
    private String dr;

}
