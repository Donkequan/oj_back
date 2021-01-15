package com.hdk.oj.service;

public interface AnswerService {

    void createFile(String filename, String content);
    void exe(String arg);
    String getResult();
}
