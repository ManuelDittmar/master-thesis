package edu.kit.service;

import edu.kit.domain.DiagramAnalysis;
import edu.kit.domain.quality.DiagramQualityCriteria;
import org.camunda.bpm.model.xml.ModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiagramAnalysisService {

    private List<DiagramQualityCriteria> diagramQualityCriteriaList;
    private  ProcessAnalysisService processAnalysisService;

    public DiagramAnalysis analyseDiagram(ModelInstance modelInstance) {

        return new DiagramAnalysis(modelInstance,processAnalysisService,diagramQualityCriteriaList);
    }

    @Autowired
    public void setProcessAnalysisService(ProcessAnalysisService processAnalysisService) {
        this.processAnalysisService = processAnalysisService;
    }

    @Autowired
    public void setDiagramQualityCriteriaList(List<DiagramQualityCriteria> diagramQualityCriteriaList) {
        this.diagramQualityCriteriaList = diagramQualityCriteriaList;
    }
}
