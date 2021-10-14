package edu.kit.domain;

import org.camunda.bpm.model.bpmn.instance.Process;


import java.util.List;

public class ProcessAnalysis {
    private String processKey;
    private String processName;
    private boolean isExecutable;
    private List<SequenceFlowDTO> sequenceFlowDTOList;

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

    public ProcessAnalysis(Process process, List<SequenceFlowDTO> sequenceFlowDTOList) {
        this.processName = process.getName();
        this.processKey = process.getId();
        this.isExecutable = process.isExecutable();
        this.sequenceFlowDTOList = sequenceFlowDTOList;
        setRightwardFlowDirection();
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

   /* public List<SequenceFlowDTO> getSequenceFlowDTOList() {
        return sequenceFlowDTOList;
    }*/

    // TODO Check if really only to the right is best practice

    private void setRightwardFlowDirection() {
        double sequenceFlowsCount = sequenceFlowDTOList.size();
        double flowDirectionRightCount = 0;
        for (SequenceFlowDTO sequenceFlowDTO:this.sequenceFlowDTOList) {
            if(sequenceFlowDTO.flowDirection.contains(FlowDirection.RIGHT)) {
                flowDirectionRightCount++;
            }
        }
        this.rightwardFlowDirection = flowDirectionRightCount/sequenceFlowsCount;

    }
}
