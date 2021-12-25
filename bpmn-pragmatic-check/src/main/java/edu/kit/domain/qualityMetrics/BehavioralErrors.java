package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.StartEvent;

import java.util.ArrayList;

public class BehavioralErrors extends QualityCriteria{

    Process process;

    public BehavioralErrors(Process process) {
        criteriaID = "Behavioral Errors";
        this.process = process;
        outliers = new ArrayList<String>();
        calculate();
    }

    // TODO Check if Start Events in Subprocess will also be taken into account

    @Override
    public void calculate() {
        for (FlowElement element: process.getChildElementsByType(StartEvent.class)) {
            StartEvent startEvent = (StartEvent) element;
            for (FlowNode node :startEvent.getSucceedingNodes().list()) {
            }
        }
    }
}
