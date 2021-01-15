package com.hdk.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdk.oj.entity.AnswerSample;

public interface AnswerSampleService extends IService<AnswerSample> {

    void createFile(String path, String filename, String content);
    String exe(String filename, String arg);
    String getResult();
    String readFile(String path);

}
