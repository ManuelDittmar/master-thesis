package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public abstract class TaskTypeDefinitionAbstract extends ConfigurableProcessQualityCriteria {

    @Value("#{'${pragmatic.taskTypeDefinition.forbiddenTaskTypes:manualTask,task,scriptTask}'.split(',')}")
    public List<String> forbiddenTaskTypes;

    public TaskTypeDefinitionAbstract() {
        super();
    }

    public TaskTypeDefinitionAbstract(Process process) {
        super(process);
    }

    @Override
    public <T extends ProcessQualityCriteria> T createInstance(Process process) {
        TaskTypeDefinitionAbstract qualityCriteria =  super.createInstance(process);
        qualityCriteria.setForbiddenTaskTypes(this.forbiddenTaskTypes);
        qualityCriteria.calculate();
        return (T) qualityCriteria;
    }

    public void setForbiddenTaskTypes(List<String> forbiddenTaskTypes) {
        this.forbiddenTaskTypes = forbiddenTaskTypes;
    }

}
