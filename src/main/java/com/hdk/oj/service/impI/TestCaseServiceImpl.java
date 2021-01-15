package com.hdk.oj.service.impI;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdk.oj.entity.TestCase;
import com.hdk.oj.mapper.TestCaseMapper;
import com.hdk.oj.service.TestCaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper, TestCase> implements TestCaseService {

    @Override
    public List<TestCase> selectByQid(int qid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("qid", qid);
        List<TestCase> testCaseList = baseMapper.selectByMap(map);
        return testCaseList;

    }

    @Override
    public void removeByQid(int qid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("qid", qid);
        removeByMap(map);
    }

}
