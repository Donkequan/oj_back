package com.hdk.oj.controller;

import com.hdk.oj.entity.TestCase;
import com.hdk.oj.result.R;
import com.hdk.oj.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin //允许跨域
@RestController
@RequestMapping("/testcase")
public class TestCaseController {
    @Autowired
    private TestCaseService testCaseService;

    @PostMapping("saveTestcase")
    public R saveTestcase(@RequestBody TestCase testCase){
        testCaseService.save(testCase);
        return R.ok().message("保存成功");
    }

    @PutMapping("update")
    public R update(@RequestBody TestCase testCase){
        testCaseService.updateById(testCase);
        return R.ok().message("修改成功");
    }

    @GetMapping("getlist/{qid}")
    public R getByQid(@PathVariable String qid){
        List<TestCase> testCaseList = testCaseService.selectByQid(Integer.parseInt(qid));
        if(testCaseList != null){
            return R.ok().message("获取成功").data("item", testCaseList);
        }else{
            return R.error().message("数据不存在");
        }
    }

    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id){
        TestCase testCase = testCaseService.getById(id);
        if(testCase != null){
            return R.ok().message("获取成功").data("item", testCase);
        }else{
            return R.error().message("数据不存在");
        }
    }

    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id){
        boolean result = testCaseService.removeById(id);
        if(result){
            return R.ok().message("删除成功");
        }else{
            return R.error().message("数据不存在");
        }
    }

}
