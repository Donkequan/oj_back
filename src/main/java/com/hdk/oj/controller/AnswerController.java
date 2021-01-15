package com.hdk.oj.controller;

import com.hdk.oj.entity.Answer;
import com.hdk.oj.entity.AnswerSample;
import com.hdk.oj.entity.Question;
import com.hdk.oj.entity.TestCase;
import com.hdk.oj.result.R;
import com.hdk.oj.service.AnswerSampleService;
import com.hdk.oj.service.AnswerService;
import com.hdk.oj.service.QuestionService;
import com.hdk.oj.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


@CrossOrigin //允许跨域
@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;
    @Autowired
    TestCaseService testCaseService;
    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerSampleService answerSampleService;

    @PostMapping("judge/java")
    public R judgeAnswer(@RequestBody Answer answer){
        int count = 0;
        List<TestCase> list = testCaseService.selectByQid(answer.getQid());
        Question question = questionService.getById(answer.getQid());
        AnswerSample answerSample = answerSampleService.getById(answer.getQid());

        int sample = question.getQCode();
        if(sample==1){
            for (int i = 0; i<answerSample.getTimes(); i++){
                TestCase testCase = new TestCase();
                Random random = new Random();
                String input = String.valueOf(random.nextInt(answerSample.getAsmax()));
                String output = answerSampleService.exe(String.valueOf(answer.getQid()), " "+input);
                System.out.println("random:"+input+"--"+output);
                testCase.setInput(input);
                testCase.setOutput(output.substring(0,output.length() - 1));
                list.add(testCase);
            }
        }
        int size = list.size();
        System.out.println(answer.getLanguage());
        answerService.createFile("./answer/java/", answer.getContent());
        for (TestCase testCase  : list) {
            answerService.exe(" "+testCase.getInput());
            String s = answerService.getResult();
            System.out.println(s);
            if(s.substring(0,s.length() - 1).equals(testCase.getOutput())){
                count++;
            }
        }
        int percentage = (count * 100)/size;
        System.out.println(count);
        return R.ok().message("submit success").data("percentage", percentage);
    }
}
