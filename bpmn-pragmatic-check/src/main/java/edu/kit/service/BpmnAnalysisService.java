package edu.kit.service;

import edu.kit.domain.DiagramAnalysis;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class BpmnAnalysisService {


    public DiagramAnalysis analyseBpmnFile(InputStream file) {
        // Read File
        BpmnModelInstance modelInstance = Bpmn.readModelFromStream(file);
        // Analyze Model
        DiagramAnalysis diagramAnalysis = new DiagramAnalysis(modelInstance);
        // Return Summary
        return diagramAnalysis;
    }
}
