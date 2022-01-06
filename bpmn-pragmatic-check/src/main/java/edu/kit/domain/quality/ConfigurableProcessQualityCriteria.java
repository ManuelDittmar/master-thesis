package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;

public abstract class ConfigurableProcessQualityCriteria extends ProcessQualityCriteria {

    public ConfigurableProcessQualityCriteria() {
        super();
    }

    public ConfigurableProcessQualityCriteria(Process process) {
        criteriaID = this.getClass().getSimpleName();
        this.process = process;
        outliers = new ArrayList();
    }
}
