package com.qilihui.forum.common;

import lombok.Data;

@Data
public class EasyWebImageUploadResult {

    private String location;

    public EasyWebImageUploadResult(){}

    public EasyWebImageUploadResult(String location){
        this.location = location;
    }
}
