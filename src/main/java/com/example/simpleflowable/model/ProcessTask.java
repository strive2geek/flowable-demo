package com.example.simpleflowable.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ProcessTask {

    String id;

    String name;

    String description;

    int priority;

    String owner;

    String assignee;

    String processInstanceId;

    String executionId;

    String taskDefinitionId;

    String processDefinitionId;

    String scopeId;

    String subScopeId;

    String scopeType;

    String scopeDefinitionId;

    Date createTime;

    String taskDefinitionKey;

    Date dueDate;

    String category;

    String parentTaskId;

    String tenantId;

    String formKey;

    Map<String, Object> taskLocalVariables;

    Map<String, Object> processVariables;

    //List<? extends IdentityLinkInfo> getIdentityLinks;

    Date claimTime;
}
