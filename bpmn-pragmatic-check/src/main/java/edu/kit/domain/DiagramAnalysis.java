package edu.kit.domain;

import edu.kit.domain.quality.CriteriaType;
import edu.kit.domain.quality.DiagramQualityCriteria;
import edu.kit.service.ProcessAnalysisService;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;

import java.util.ArrayList;
import java.util.List;


public class DiagramAnalysis {

    private boolean mandatoryCriteriaNotMet;
    private List<DiagramQualityCriteria> diagramQualityCriteriaList;
    private List<ProcessAnalysis> processAnalysisList;

    public DiagramAnalysis(ModelInstance modelInstance, ProcessAnalysisService processAnalysisService, List<DiagramQualityCriteria> diagramQualityCriteriaList) {
        this.diagramQualityCriteriaList = new ArrayList<>();
        this.mandatoryCriteriaNotMet = false;
        diagramQualityCriteriaList.forEach(diagramCriteria -> {
            DiagramQualityCriteria criteria = diagramCriteria.createInstance(modelInstance);
            this.diagramQualityCriteriaList.add(criteria);
            if(criteria.getCriteriaType().equals(CriteriaType.MANDATORY) && criteria.getScore() < 1.0) {
                this.mandatoryCriteriaNotMet = true;
            }
        });

        List<Process> processes = (List<Process>) modelInstance.getModelElementsByType(Process.class);
        this.processAnalysisList = new ArrayList<>();
        for (Process process : processes) {
            ProcessAnalysis processAnalysis = processAnalysisService.analyseProcess(process);
            this.processAnalysisList.add(processAnalysis);
            if(processAnalysis.isMandatoryCriteriaNotMet()) {
                this.mandatoryCriteriaNotMet = true;
            }
        }
    }

    public boolean isMandatoryCriteriaNotMet() {
        return mandatoryCriteriaNotMet;
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
