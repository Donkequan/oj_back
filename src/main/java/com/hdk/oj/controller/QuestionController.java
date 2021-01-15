package com.hdk.oj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdk.oj.entity.AnswerSample;
import com.hdk.oj.entity.Question;
import com.hdk.oj.entity.QuestionDesc;
import com.hdk.oj.entity.QuestionInfor;
import com.hdk.oj.result.R;
import com.hdk.oj.service.AnswerSampleService;
import com.hdk.oj.service.QuestionDescService;
import com.hdk.oj.service.QuestionService;
import com.hdk.oj.service.TestCaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin //允许跨域
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionDescService questionDescService;

    @Autowired
    private AnswerSampleService answerSampleService;

    @Autowired
    private TestCaseService testCaseService;


    @GetMapping("list")
    public R listAll(){
        List<Question> list = questionService.list();
        System.out.println("==============");
        for (Question question : list) {
            System.out.println(question);
        }
        return R.ok().data("items", list);
    }

    @GetMapping("list/{page}/{limit}")
    public R listPage(@PathVariable Long page,
                      @PathVariable Long limit,
                      Question question){
        System.out.println(question);
        Page<Question> pageParam = new Page<>(page, limit);
        IPage<Question> pageModel = questionService.selectPage(pageParam, question);
        List<Question> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @GetMapping("public/list/{page}/{limit}")
    public R publicListPage(@PathVariable Long page,
                      @PathVariable Long limit,
                      Question question){
        question.setQPublic(1);
        System.out.println(question);
        Page<Question> pageParam = new Page<>(page, limit);
        IPage<Question> pageModel = questionService.selectPublicPage(pageParam, question);
        List<Question> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id){
        testCaseService.removeByQid(Integer.parseInt(id));
        boolean result = questionService.removeById(id);
        questionDescService.removeById(id);
//        answerSampleService.removeById(id);
        if(result){
            return R.ok().message("删除成功");
        }else{
            return R.error().message("数据不存在");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("saveQuestion")
    public R saveQuestion(@RequestBody QuestionInfor questionInfor){
        Question question = new Question();
        BeanUtils.copyProperties(questionInfor, question);
        questionService.save(question);
        System.out.println(questionInfor);

        int qid = question.getQid();
        QuestionDesc questionDesc = new QuestionDesc();
        BeanUtils.copyProperties(questionInfor,questionDesc);
        questionDesc.setQid(qid);
        questionDescService.save(questionDesc);

        Integer sample = question.getQCode();
        if(sample == 1){
            AnswerSample answerSample = new AnswerSample();
            BeanUtils.copyProperties(questionInfor, answerSample);
            answerSample.setQid(qid);
            answerSampleService.save(answerSample);
            answerSampleService.createFile("./answer/sample/java/", String.valueOf(qid), questionInfor.getSample());
        }

        return R.ok().message("保存成功").data("qid", qid);
    }

    @Transactional(rollbackFor = Exception.class)
    @PutMapping("update")
    public R updateById(@RequestBody QuestionInfor questionInfor){
        Question question = new Question();
        BeanUtils.copyProperties(questionInfor, question);
        boolean result = questionService.updateById(question);

        int qid = question.getQid();
        QuestionDesc questionDesc = new QuestionDesc();
        BeanUtils.copyProperties(questionInfor,questionDesc);
        questionDesc.setQid(qid);
        questionDescService.updateById(questionDesc);

        Integer sample = question.getQCode();
        if(sample == 1){
            AnswerSample answerSample = new AnswerSample();
            BeanUtils.copyProperties(questionInfor, answerSample);
            answerSample.setQid(qid);
            AnswerSample have_answer = answerSampleService.getById(qid);
            if(have_answer == null) {
                answerSampleService.save(answerSample);
            } else{
                answerSampleService.updateById(answerSample);
            }
            answerSampleService.createFile("./answer/sample/java/", String.valueOf(qid), questionInfor.getSample());
        }

        if(result){
            return R.ok().message("修改成功");
        }else{
            return R.error().message("数据不存在");
        }
    }

    @GetMapping("get/{id}")
    public R getById(@PathVariable String id){
        QuestionInfor questionInfor = new QuestionInfor();
        Question question = questionService.getById(id);
        QuestionDesc questionDesc = questionDescService.getById(id);
        if(question.getQCode()==1){
            AnswerSample answerSample = answerSampleService.getById(id);
            String sample = answerSampleService.readFile("./answer/sample/java/"+id);
            BeanUtils.copyProperties(answerSample, questionInfor);
            questionInfor.setSample(sample);
        }
        BeanUtils.copyProperties(question, questionInfor);
        BeanUtils.copyProperties(questionDesc, questionInfor);
        if(question != null){
            return R.ok().data("item", questionInfor);
        }else{
            return R.error().message("数据不存在");
        }
    }

    @GetMapping("list/name/{key}")
    public R selectNameListByKey(@PathVariable String key){
        List<Map<String, Object>> nameList = questionService.selectNameList(key);

        return R.ok().data("titleList", nameList);
    }

}
