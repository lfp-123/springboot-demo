package com.luoyu.swagger;

import lombok.Data;

import java.util.List;

/**
 * @author ${林锋鹏}
 * @Title: JsonObject
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/5 21:51
 */
@Data
public class JsonObject {

    private String error;
    private String status;
    private List<results> results;

  @Data
    class results {
        private String  currentCity;
        private List<index> index;


    }
    @Data
    class index{
        private String title;
        private String zs;
        private String tipt;
        private String des;

    }
 }
