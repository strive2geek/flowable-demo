package com.example.simpleflowable.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Deploy {

    String id;

    String name;

    Date deploymentTime;

    String category;

    String key;

    String derivedFrom;

    String derivedFromRoot;

    String tenantId;

    String engineVersion;

    boolean isNew;
}
