package edu.kit.domain.qualityMetrics;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.glassfish.jersey.internal.jsr166.Flow;

public class CompleteLabeling implements QualityCriteria{

    String criteriaID = "Complete Labeling";
    Process process;
    private List<String> outliers;
    private double completeLabeling;

    public CompleteLabeling(Process process) {
        this.process = process;
        outliers = new ArrayList<String>();
        calculate();
    }

    // TODO joining gateways and sequence flows that are not from a gateway should be excluded here
    @Override
    public void calculate() {
        double elementsCount = process.getFlowElements().size();
        for (FlowElement element:process.getFlowElements()) {
            if (element.getName() == null) {
                // Merging Gateways do not need labeling
                if (element.getElementType().getBaseType().getTypeName().equals("gateway")) {
                    Gateway gateway = (Gateway) element;
                    if (gateway.getSucceedingNodes().list().size() > 1) {
                        outliers.add(element.getId());
                    }
                }
                // Sequence Flows that are not coming from a gateway do not need labeling
                if (element.getElementType().getBaseType().getTypeName().equals("flowElement")) {
                    SequenceFlow sequenceFlow = (SequenceFlow) element;
                    String sourceType = sequenceFlow.getSource().getElementType().getBaseType().getTypeName();
                    if (sourceType.equals("gateway")) {
                        Gateway gateway = (Gateway) sequenceFlow.getSource();
                        if(gateway.getSucceedingNodes().list().size() > 1) {
                            outliers.add(element.getId());
                        }
                    }
                }
            }
        }
        double outliersCount = outliers.size();
        this.completeLabeling = (elementsCount-outliersCount)/elementsCount;


    }

    public String getCriteriaID() {
        return criteriaID;
    }

    @Override
    public double getScore() {
        return this.completeLabeling;
    }

    @Override
    public List getOutliers() {
        return this.outliers;
    }
}
