package edu.kit.domain;

import edu.kit.domain.qualityMetrics.*;
import org.camunda.bpm.model.bpmn.instance.Process;


import java.util.ArrayList;
import java.util.List;

public class ProcessAnalysis {
    private final String processKey;
    private final String processName;
    private final boolean isExecutable;
    private final List<SequenceFlowDTO> sequenceFlowDTOList;
    private final List<QualityCriteria> qualityCriteriaList;

    // TODO make a configuration for addting the criteria
    public ProcessAnalysis(Process process, List<SequenceFlowDTO> sequenceFlowDTOList) {
        this.processName = process.getName();
        this.processKey = process.getId();
        this.isExecutable = process.isExecutable();
        this.sequenceFlowDTOList = sequenceFlowDTOList;
        this.qualityCriteriaList = new ArrayList<>();
        // Here you can add the criterias you want to include in your process analysis
        qualityCriteriaList.add(new RightwardFlowDirection(sequenceFlowDTOList));
        qualityCriteriaList.add(new CompleteLabeling(process));
        qualityCriteriaList.add(new EdgeCrossing(sequenceFlowDTOList));
        qualityCriteriaList.add(new EdgeOrthogonality(sequenceFlowDTOList));
        qualityCriteriaList.add(new TaskTypeDefinition(process));
        qualityCriteriaList.add(new ImplementationVisibility(process));
        qualityCriteriaList.add(new Explicitness(process));
        qualityCriteriaList.add(new BehavioralErrors(process));
        qualityCriteriaList.add(new HumanTaskAssignment(process));
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
