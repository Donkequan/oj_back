package com.hdk.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdk.oj.entity.TestCase;

import java.util.List;

public interface TestCaseService extends IService<TestCase> {

    List<TestCase> selectByQid(int qid);

    void removeByQid(int qid);
}
