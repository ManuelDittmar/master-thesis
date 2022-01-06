package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;


public class BehavioralErrors extends ProcessQualityCriteria {

    public BehavioralErrors(Process process) {
        super(process);
    }

    // TODO Check if Start Events in Subprocess will also be taken into account

    @Override
    public void init() {
        for (FlowElement element : process.getFlowElements()) {
            System.out.println(element.getElementType().getBaseType().getInstanceType());

        }
    }
}
