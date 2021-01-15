package com.hdk.oj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hdk.oj.entity.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService extends IService<Question> {

    IPage<Question> selectPage(Page<Question> pageParam, Question question);

    List<Map<String, Object>> selectNameList(String key);

    IPage<Question> selectPublicPage(Page<Question> pageParam, Question question);
}
