package edu.kit.service;

import edu.kit.domain.DiagramAnalysis;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class BpmnAnalysisService {

    DiagramAnalysisService diagramAnalysisService;


    public DiagramAnalysis analyseBpmnFile(InputStream file) {
        // Read File
        BpmnModelInstance modelInstance = Bpmn.readModelFromStream(file);
        // Analyze Model
       return diagramAnalysisService.analyseDiagram(modelInstance);

    }

    @Autowired
    public void setDiagramAnalysisService(DiagramAnalysisService diagramAnalysisService) {
        this.diagramAnalysisService = diagramAnalysisService;
    }
}
