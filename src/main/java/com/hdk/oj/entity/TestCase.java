package com.hdk.oj.entity;

//CREATE TABLE IF NOT EXISTS `test_case`(
//        `tcid` INT UNSIGNED AUTO_INCREMENT,
//        `input` VARCHAR(100) NOT NULL,
//        `output` VARCHAR(100) NOT NULL,
//        `qid` INT UNSIGNED,
//        FOREIGN KEY(qid) REFERENCES question(qid),
//        PRIMARY KEY ( `tcid` )
//        )ENGINE=INNODB DEFAULT CHARSET=utf8;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("test_case")
@Accessors(chain = true)
public class TestCase {
    @TableId(value = "tcid", type = IdType.AUTO)
    private int tcid;
    private String input;
    private String output;
    private int qid;
}
