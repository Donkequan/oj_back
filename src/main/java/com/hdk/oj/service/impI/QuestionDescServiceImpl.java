package com.hdk.oj.service.impI;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdk.oj.entity.QuestionDesc;
import com.hdk.oj.mapper.QuestionDescMapper;
import com.hdk.oj.service.QuestionDescService;
import org.springframework.stereotype.Service;

@Service
public class QuestionDescServiceImpl extends ServiceImpl<QuestionDescMapper, QuestionDesc> implements QuestionDescService {
}
