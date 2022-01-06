package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;

public abstract class ConfigurableQualityCriteria extends QualityCriteria {

    public ConfigurableQualityCriteria() {
        super();
    }

    public ConfigurableQualityCriteria(Process process) {
        criteriaID = this.getClass().getSimpleName();
        this.process = process;
        outliers = new ArrayList();
    }
}
