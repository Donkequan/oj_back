package com.hdk.oj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("question_desc")
@Accessors(chain = true)
public class QuestionDesc {
    @TableId(value = "qid")
    private int qid;
    private String qDescription;
    private String qHint;
}
