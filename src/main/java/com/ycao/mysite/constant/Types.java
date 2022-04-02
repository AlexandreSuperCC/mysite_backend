package com.ycao.mysite.constant;

public enum Types {

    IMAGES("images"),
    FILE("file");

    private String type;
    Types(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
