package com.example.simpleflowable;

import com.example.simpleflowable.service.IWorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.task.Comment;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SimpleFlowableApplicationTests {

    @Autowired
    IWorkflowService workflowService;

    @Test
    public void contextLoads1() {

    }


    @Test
    public void commentTest() {
        String businessKey = "holidayRequest001";
        List<Comment> commentList = workflowService.getHisComments(businessKey);
        for(Comment c : commentList) {
            log.info("==={} {} {} {} ===", c.getId(), c.getUserId(), c.getFullMessage(), c.getTime());
        }
    }
}
