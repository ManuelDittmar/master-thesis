package edu.kit.service;

import edu.kit.domain.ProcessAnalysis;
import edu.kit.domain.quality.ProcessQualityCriteria;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessAnalysisService {

    private List<ProcessQualityCriteria> processQualityCriteriaList;

    @Autowired
    public void setQualityCriteriaList(List<ProcessQualityCriteria> processQualityCriteriaList) {
        this.processQualityCriteriaList = processQualityCriteriaList;
    }

    public ProcessAnalysis analyseProcess(Process process) {
        return new ProcessAnalysis(process, processQualityCriteriaList);
    }
}
