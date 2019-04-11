package com.example.simpleflowable.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProcessDef {
    /**
     * category name which is derived from the targetNamespace attribute in the definitions element
     */
    String category;

    /** label used for display purposes */
    String name;

    /** unique name for all versions this process definitions */
    String key;

    /** description of this process **/
    String description;

    /** version of this process definition */
    int version;

    /**
     * name of {@link RepositoryService#getResourceAsStream(String, String) the resource} of this process definition.
     */
    String resourceName;

    /** The deployment in which this process definition is contained. */
    String deploymentId;

    /** The resource name in the deployment of the diagram image (if any). */
    String diagramResourceName;

    /**
     * Does this process definition has a {@link FormService#getStartFormData(String) start form key}.
     */
    boolean hasStartFormKey;

    /**
     * Does this process definition has a graphical notation defined (such that a diagram can be generated)?
     */
    boolean hasGraphicalNotation;

    /** Returns true if the process definition is in suspended state. */
    boolean isSuspended;

    /** The tenant identifier of this process definition */
    String tenantId;

    /** The derived from process definition value when this is a dynamic process definition */
    String derivedFrom;

    /** The root derived from process definition value when this is a dynamic process definition */
    String derivedFromRoot;

    /** The derived version of the process definition */
    int derivedVersion;

    /** The engine version for this process definition (5 or 6) */
    String engineVersion;
}
