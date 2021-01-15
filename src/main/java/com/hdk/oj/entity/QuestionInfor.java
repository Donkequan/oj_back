package com.hdk.oj.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QuestionInfor {
    private int qid;
    private String qTitle;
    private String qDescription;
    private int qTime;  //运行最大时间
    private String qCourse;
    private String qType;
    private String qHint;
    private Integer qLevel;
    private Integer qPublic;
    private Integer qCode;
    private String sampleLanguage;
    private Integer asmin;
    private Integer asmax;
    private String sample;
    private Integer times;
}
