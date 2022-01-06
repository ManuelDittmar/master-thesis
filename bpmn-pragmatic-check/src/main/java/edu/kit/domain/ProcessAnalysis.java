package edu.kit.domain;

import edu.kit.domain.qualityMetrics.*;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;
import java.util.List;

public class ProcessAnalysis {
    private Process process;
    private String processKey;
    private String processName;
    private boolean isExecutable;
    private  List<ProcessQualityCriteria> processQualityCriteriaList;

    // TODO make a configuration for addting the criteria
    public ProcessAnalysis(Process process, List<ProcessQualityCriteria> processQualityCriteriaList) {
        this.process = process;
        this.processName = process.getName();
        this.processKey = process.getId();
        this.isExecutable = process.isExecutable();
        this.processQualityCriteriaList = new ArrayList<>();
        processQualityCriteriaList.forEach(qualityCriteria -> {
            this.processQualityCriteriaList.add(qualityCriteria.createInstance(process));
        });
    }

    public String getProcessKey() {
        return processKey;
    }

    public String getProcessName() {
        return processName;
    }

    public boolean isExecutable() {
        return isExecutable;
    }

    public List<ProcessQualityCriteria> getQualityCriteriaList() {
        return processQualityCriteriaList;
    }

}
