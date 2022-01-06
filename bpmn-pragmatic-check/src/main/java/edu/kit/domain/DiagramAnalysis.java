package edu.kit.domain;

import edu.kit.domain.qualityMetrics.DiagramQualityCriteria;
import edu.kit.service.ProcessAnalysisService;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;

import java.util.ArrayList;
import java.util.List;


public class DiagramAnalysis {

    private List<DiagramQualityCriteria> diagramQualityCriteriaList;
    private List<ProcessAnalysis> processAnalysisList;

    public DiagramAnalysis(ModelInstance modelInstance, ProcessAnalysisService processAnalysisService, List<DiagramQualityCriteria> diagramQualityCriteriaList) {

        this.diagramQualityCriteriaList = new ArrayList<>();
        diagramQualityCriteriaList.forEach(criteria -> this.diagramQualityCriteriaList.add(criteria.createInstance(modelInstance)));

        List<Process> processes = (List<Process>) modelInstance.getModelElementsByType(Process.class);
        this.processAnalysisList = new ArrayList<>();
        for (Process process:processes) {
            ProcessAnalysis processAnalysis = processAnalysisService.analyseProcess(process);
            this.processAnalysisList.add(processAnalysis);
        }
    }

    public void setProcessAnalysisList(List<ProcessAnalysis> processAnalysisList) {
        this.processAnalysisList = processAnalysisList;
    }

    public void setDiagramQualityCriteriaList(List<DiagramQualityCriteria> diagramQualityCriteriaList) {
        this.diagramQualityCriteriaList = diagramQualityCriteriaList;
    }

    public List<ProcessAnalysis> getProcessAnalysisList() {
        return processAnalysisList;
    }

    public List<DiagramQualityCriteria> getDiagramQualityCriteriaList() {
        return diagramQualityCriteriaList;
    }
}
