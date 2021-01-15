package com.hdk.oj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("answer_sample")
@Accessors(chain = true)
public class AnswerSample {
    @TableId(value = "qid")
    private Integer qid;
    private String sampleLanguage;
    private Integer asmin;
    private Integer asmax;
    private Integer times;
}
