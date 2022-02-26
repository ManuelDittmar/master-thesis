package edu.kit.domain;

import edu.kit.domain.common.CriteriaType;
import edu.kit.domain.quality.*;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProcessAnalysis {
    private final String processKey;
    private final String processName;
    private final boolean isExecutable;
    private boolean mandatoryCriteriaNotMet;
    private final List<ProcessQualityCriteria> processQualityCriteriaList;

    public ProcessAnalysis(Process process, List<ProcessQualityCriteria> processQualityCriteriaList) {
        this.processName = process.getName();
        this.processKey = process.getId();
        this.isExecutable = process.isExecutable();
        this.mandatoryCriteriaNotMet = false;
        this.processQualityCriteriaList = new ArrayList<>();
        processQualityCriteriaList.forEach(qualityCriteria -> {
            ProcessQualityCriteria criteria = qualityCriteria.createInstance(process);
            this.processQualityCriteriaList.add(criteria);
            if(criteria.getCriteriaType().equals(CriteriaType.MANDATORY) && criteria.getScore() < 1.0) {
                this.mandatoryCriteriaNotMet = true;
            }
        });
        this.processQualityCriteriaList.sort(Comparator.comparing(ProcessQualityCriteria::getCriteriaType).thenComparing(ProcessQualityCriteria::getScore));
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

    public boolean isMandatoryCriteriaNotMet() {
        return mandatoryCriteriaNotMet;
    }

    public List<ProcessQualityCriteria> getQualityCriteriaList() {
        return processQualityCriteriaList;
    }

}
