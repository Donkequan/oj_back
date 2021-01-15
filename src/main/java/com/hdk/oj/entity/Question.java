package com.hdk.oj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

//CREATE TABLE IF NOT EXISTS `question`(
//        `qid` INT UNSIGNED AUTO_INCREMENT,
//        `q_title` VARCHAR(100) NOT NULL,
//        `q_description` VARCHAR(255) NOT NULL,
//        PRIMARY KEY ( `qid` )
//        )ENGINE=INNODB DEFAULT CHARSET=utf8;

@Data
@TableName("question")
@Accessors(chain = true)
public class Question implements Serializable {
    @TableId(value = "qid", type = IdType.AUTO)
    private int qid;
    private String qTitle;
    private int qTime;
    private String qCourse;
    private String qType;
    private Integer qLevel;
    private Integer qPublic;
    private Integer qCode;
}
