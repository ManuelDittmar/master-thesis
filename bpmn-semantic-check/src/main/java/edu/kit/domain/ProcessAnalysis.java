package edu.kit.domain;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.List;

public class ProcessAnalysis {
    private String processKey;
    private String processName;
    private boolean isExecutable;

    private double coefficientOfConnectivity;
    private double completeLabeling;
    private double cyclicity;
    private double density;
    private double rightwardFlowDirection;
    private double edgeCrossings;
    private double edgeOrthogonality;
    private double minimumAngle;
    private double sourcesAndSinks;
    private double separability;
    private double sequentiality;
    private double size;

    public ProcessAnalysis(Process process) {
        this.processName = process.getName();
        this.processKey = process.getId();
        this.isExecutable = process.isExecutable();
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

    public double getCoefficientOfConnectivity() {
        return coefficientOfConnectivity;
    }

    public double getCompleteLabeling() {
        return completeLabeling;
    }

    public double getCyclicity() {
        return cyclicity;
    }

    public double getDensity() {
        return density;
    }

    public double getRightwardFlowDirection() {
        return rightwardFlowDirection;
    }

    public double getEdgeCrossings() {
        return edgeCrossings;
    }

    public double getEdgeOrthogonality() {
        return edgeOrthogonality;
    }

    public double getMinimumAngle() {
        return minimumAngle;
    }

    public double getSourcesAndSinks() {
        return sourcesAndSinks;
    }

    public double getSeparability() {
        return separability;
    }

    public double getSequentiality() {
        return sequentiality;
    }

    public double getSize() {
        return size;
    }
}
