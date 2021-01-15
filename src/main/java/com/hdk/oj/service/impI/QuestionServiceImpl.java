package com.hdk.oj.service.impI;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdk.oj.entity.Question;
import com.hdk.oj.mapper.QuestionMapper;
import com.hdk.oj.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Override
    public IPage<Question> selectPage(Page<Question> pageParam, Question question) {
//        1、排序：按照sort字段排序
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("qid");
//        2、分页查询
        if(question == null){
            return baseMapper.selectPage(pageParam, queryWrapper);
        }
//        3、条件查询
        String title = question.getQTitle();
        if(!StringUtils.isEmpty(title)){
            queryWrapper.likeRight("q_title", title);
        }
        Integer level = question.getQLevel();
        if(level != null){
            queryWrapper.eq("q_level", level);
        }
        Integer qpublic = question.getQPublic();
        if(qpublic != null){
            queryWrapper.eq("q_public", qpublic);
        }
        return baseMapper.selectPage(pageParam, queryWrapper);
    }


    @Override
    public List<Map<String, Object>> selectNameList(String key) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("q_title");
        queryWrapper.likeRight("q_title", key);
        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);
        return list;
    }

    @Override
    public IPage<Question> selectPublicPage(Page<Question> pageParam, Question question) {
        //        1、排序：按照sort字段排序
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("qid");
//        2、分页查询
        if(question == null){
            return baseMapper.selectPage(pageParam, queryWrapper);
        }
//        3、条件查询
        String title = question.getQTitle();
        if(!StringUtils.isEmpty(title)){
            queryWrapper.likeRight("q_title", title);
        }
        Integer level = question.getQLevel();
        if(level != null){
            queryWrapper.eq("q_level", level);
        }
        Integer qpublic = question.getQPublic();
        if(qpublic != null){
            queryWrapper.eq("q_public", 1);
        }
        return baseMapper.selectPage(pageParam, queryWrapper);
    }
}
