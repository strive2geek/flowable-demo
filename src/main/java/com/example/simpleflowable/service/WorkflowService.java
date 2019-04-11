package com.example.simpleflowable.service;

import com.example.simpleflowable.model.Deploy;
import com.example.simpleflowable.model.ProcessDef;
import com.example.simpleflowable.model.ProcessTask;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("workflowService")
public class WorkflowService implements IWorkflowService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Override
    public List<Deploy> getDeploy() {
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        List<Deploy> result = new ArrayList<>();
        for(Deployment item : list) {
            Deploy deploy = new Deploy();
            BeanUtils.copyProperties(item, deploy);
            result.add(deploy);
        }
        return result;
    }

    @Override
    public List<ProcessDef> getProcessDefs() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .list();
        List<ProcessDef> result = new ArrayList<>();
        for(ProcessDefinition item : list) {
            ProcessDef def = new ProcessDef();
            BeanUtils.copyProperties(item, def);
            result.add(def);
        }
        return result;
    }

    @Override
    public boolean startProcessByKey(String key, String users, String groups, String businessKey, Map<String, Object> variables) {
        mapVarWrap(users, groups, variables);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, businessKey, variables);
        return processInstance != null;
    }

    @Override
    public List<ProcessTask> getTasksAll(String user, String group) {
        TaskQuery taskQuery = taskService
                .createTaskQuery();
        List<ProcessTask> result = new ArrayList<>();

        //taskMap去重
        Map<String, Task> taskMap = new HashMap<>();
        if(!StringUtils.isEmpty(user)) {
            //.taskCandidateGroup("managers").list();
            //.taskCandidateUser("employee001")
            List<Task> userTask = taskQuery.taskCandidateOrAssigned(user)
                    //.taskCandidateGroup("group4")
                    //.taskAssignee("employee002")
                    .orderByTaskCreateTime().desc()
                    .list();
            for(Task item : userTask) {
                taskMap.put(item.getId(), item);
            }
        }
        if(!StringUtils.isEmpty(group)) {
            List<Task> groupTask = taskQuery.taskCandidateGroup(group)
                    //.taskAssignee("employee002")
                    //.taskCandidateOrAssigned(users)
                    .orderByTaskCreateTime().desc()
                    .list();
            for(Task item : groupTask) {
                taskMap.put(item.getId(), item);
            }
        }
        for(Map.Entry<String, Task> entry : taskMap.entrySet()) {
            ProcessTask task  = new ProcessTask();
            BeanUtils.copyProperties(entry.getValue(), task);
            result.add(task);
        }
        return result;
    }

    @Override
    public  List<ProcessTask> getTasksOfUser(String user) {
        List<Task> userTask = taskService
                .createTaskQuery()
                .taskCandidateOrAssigned(user)
                .orderByTaskCreateTime().desc()
                .list();
        return translateTask(userTask);
    }

    @Override
    public  List<ProcessTask> getTasksOfGroup(String group) {
        List<Task> groupTask = taskService
                .createTaskQuery()
                .taskCandidateGroup(group)
                .orderByTaskCreateTime().desc()
                .list();

        return translateTask(groupTask);
    }

    private List<ProcessTask> translateTask(List<Task> tasks) {
        List<ProcessTask> result = new ArrayList<>();
        for(Task item : tasks) {
            ProcessTask task  = new ProcessTask();
            BeanUtils.copyProperties(item, task);
            result.add(task);
        }
        return result;
    }

    @Override
    public boolean completeTask(String taskId, String user, String comment, String nextUsers, String nextGroups, Map<String, Object> variables) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null) {
            return false;
        }
        mapVarWrap(nextUsers, nextGroups, variables);
        Authentication.setAuthenticatedUserId(user);
        taskService.addComment(taskId, task.getProcessInstanceId(), comment);
        taskService.complete(taskId, variables);
        task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return task == null;
    }

    /*变量包装*/
    private Map<String, Object> mapVarWrap(String nextUsers, String nextGroups, Map<String, Object> variables) {
        if(!StringUtils.isEmpty(nextUsers)) {
            if(variables == null) {
                variables = new HashMap<>();
            }
            variables.put("users", nextUsers);
        }
        if(!StringUtils.isEmpty(nextGroups)) {
            if(variables == null) {
                variables = new HashMap<>();
            }
            variables.put("groups", nextGroups);
        }
        return variables;
    }

    @Override
    public  List<Comment> getHisComments(String businessKey) {
        HistoricProcessInstance istoricProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        if(istoricProcessInstance == null) {
            return null;
        }
        String processInstanceId = istoricProcessInstance.getId();
        List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
        return comments;
    }

/*    @Transactional
    public void startProcess() {
        runtimeService.startProcessInstanceByKey("oneTaskProcess");
    }*/
}
