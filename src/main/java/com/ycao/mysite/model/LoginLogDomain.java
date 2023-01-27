package com.ycao.mysite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLogDomain {
    private int lgid;
    private String userId;
    private String loginIp;
    private String loginTime;
}
