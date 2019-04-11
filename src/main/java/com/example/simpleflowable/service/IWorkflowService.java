package com.example.simpleflowable.service;

import com.example.simpleflowable.model.Deploy;
import com.example.simpleflowable.model.ProcessDef;
import com.example.simpleflowable.model.ProcessTask;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.task.Comment;

import java.util.List;
import java.util.Map;

public interface IWorkflowService {

    /*获取发布列表*/
    List<Deploy> getDeploy();

    /*获取流程定义列表*/
    List<ProcessDef> getProcessDefs();

    /*根据流程定义key启动流程, 指定处理人和组*/
    boolean startProcessByKey(String key, String users, String groups, String businessKey, Map<String, Object> variables);

    /*根据user或group获取任务列表*/
    List<ProcessTask> getTasksAll(String user, String group);

    /*根据user获取任务列表*/
    List<ProcessTask> getTasksOfUser(String user);

    /*根据group获取任务列表*/
    List<ProcessTask> getTasksOfGroup(String group);

    /*完成任务, 指定下个节点处理人和组*/
    boolean completeTask(String taskId, String user, String comment, String nextUsers, String nextGroups, Map<String, Object> variables);

    /*评论查看*/
    List<Comment> getHisComments(String businessKey);
}
