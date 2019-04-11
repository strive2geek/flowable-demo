package com.example.simpleflowable.controller;

import com.example.simpleflowable.model.Deploy;
import com.example.simpleflowable.model.ProcessDef;
import com.example.simpleflowable.model.ProcessTask;
import com.example.simpleflowable.service.IWorkflowService;
import org.flowable.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class WorkflowController {

    @Autowired
    IWorkflowService workflowService;

    @RequestMapping("/getDeploys")
    @ResponseBody
    public List<Deploy> getDeploy() {
        return workflowService.getDeploy();
    }

    @RequestMapping("/getProcessDefs")
    @ResponseBody
    public List<ProcessDef> getProcessDefs() {
        return workflowService.getProcessDefs();
    }

    @RequestMapping("/startProcess")
    @ResponseBody
    public boolean startProcess(String key, String user, String group, String businessKey, @RequestParam  Map<String, Object> variable) {
        return workflowService.startProcessByKey(key, user, group, businessKey, variable);
    }

    @RequestMapping("/getTasks")
    @ResponseBody
    public List<ProcessTask> getTask(String user, String group) {
        return workflowService.getTasksAll(user, group);
    }

    @RequestMapping("/completeTask")
    @ResponseBody
    public boolean completeTask(String taskId, String user, String comment, String nextUsers, String nextGroups, @RequestParam Map<String, Object> variable) {
        variable.put("approved", true);
        return workflowService.completeTask(taskId, user, comment, nextUsers, nextGroups, variable);
    }

    @RequestMapping("/getHisActs")
    @ResponseBody
    public List<Comment> getHisActs(String processInstanceId) {
        return workflowService.getHisComments(processInstanceId);
    }
}
