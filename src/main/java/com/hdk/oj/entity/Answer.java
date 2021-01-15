package com.hdk.oj.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Answer {

    private int qid;
    private String language;
    private String content;

}
