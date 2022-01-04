package edu.kit.service;

import edu.kit.domain.ProcessAnalysis;
import edu.kit.domain.qualityMetrics.QualityCriteria;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessAnalysisService {

    private List<QualityCriteria> qualityCriteriaList;

    @Autowired
    public void setQualityCriteriaList(List<QualityCriteria> qualityCriteriaList) {
        this.qualityCriteriaList = qualityCriteriaList;
    }

    public ProcessAnalysis analyseProcess(Process process) {
        return new ProcessAnalysis(process, qualityCriteriaList);
    }
}
