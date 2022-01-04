package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;


public class BehavioralErrors extends QualityCriteria{

    public BehavioralErrors(Process process) {
        super(process);
        calculate();
    }

    // TODO Check if Start Events in Subprocess will also be taken into account

    @Override
    public void calculate() {
        for (FlowElement element: process.getFlowElements()) {
            System.out.println(element.getElementType().getBaseType().getInstanceType());

        }
    }
}
