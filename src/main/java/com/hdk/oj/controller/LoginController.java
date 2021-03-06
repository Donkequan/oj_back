package com.hdk.oj.controller;

import com.hdk.oj.result.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin //跨域
@RestController
@RequestMapping("/user")
public class LoginController {

    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok()
                .data("roles","[teacher]")
                .data("name","hdk")
                .data("kkkk","kkkkk")
                .data("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }
}
