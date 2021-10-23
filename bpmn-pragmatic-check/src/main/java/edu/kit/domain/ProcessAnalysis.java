package edu.kit.domain;

import edu.kit.domain.qualityMetrics.CompleteLabeling;
import edu.kit.domain.qualityMetrics.QualityCriteria;
import edu.kit.domain.qualityMetrics.RightwardFlowDirection;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.Process;


import java.util.ArrayList;
import java.util.List;

public class ProcessAnalysis {
    private String processKey;
    private String processName;
    private boolean isExecutable;
    private List<SequenceFlowDTO> sequenceFlowDTOList;
    private List<QualityCriteria> qualityCriteriaList;

    public ProcessAnalysis(Process process, List<SequenceFlowDTO> sequenceFlowDTOList) {
        this.processName = process.getName();
        this.processKey = process.getId();
        this.isExecutable = process.isExecutable();
        this.sequenceFlowDTOList = sequenceFlowDTOList;
        this.qualityCriteriaList = new ArrayList<>();
        // Here you can add the criterias you want to include in your process analysis
        qualityCriteriaList.add(new RightwardFlowDirection(sequenceFlowDTOList));
        qualityCriteriaList.add(new CompleteLabeling(process));

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

    public List<QualityCriteria> getQualityCriteriaList() {
        return qualityCriteriaList;
    }
}
