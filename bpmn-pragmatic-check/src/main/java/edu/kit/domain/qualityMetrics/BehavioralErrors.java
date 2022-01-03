package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnEdge;


public class BehavioralErrors extends QualityCriteria{

    public BehavioralErrors(Process process) {
        super(process);
        calculate();
    }

    // TODO Check if Start Events in Subprocess will also be taken into account

    @Override
    public void calculate() {
        for (FlowElement element: process.getChildElementsByType(StartEvent.class)) {
            StartEvent startEvent = (StartEvent) element;
            process.getModelInstance().getModelElementsByType(BpmnEdge.class).forEach(edge -> System.out.println(edge.getId()));
            for (FlowNode node :startEvent.getSucceedingNodes().list()) {
            }
        }
    }
}
