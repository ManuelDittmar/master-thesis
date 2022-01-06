package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Explicitness extends ProcessQualityCriteria {

    public Explicitness() {
        super();
    }

    public Explicitness(Process process) {
        super(process);
    }
    @Override
    public void calculate() {
        List<FlowElement> flowElements = getAllFlowElements(process);

        List<FlowNode> flowNodes = flowElements.stream()
                .filter(flowElement -> !flowElement.getElementType().getInstanceType().equals(SequenceFlow.class))
                .filter(flowElement -> !flowElement.getElementType().getBaseType().getInstanceType().equals(Gateway.class))
                .map(flowElement -> (FlowNode) flowElement)
                .collect(Collectors.toList());

        flowNodes.stream().filter(flowNode -> flowNode.getOutgoing().size() > 1 || flowNode.getIncoming().size() > 1)
                .forEach(flowNode -> {
                    Set<String> sequenceFlows = new HashSet<>();
                    if (flowNode.getOutgoing().size() > 1) {
                        sequenceFlows
                                .addAll(flowNode.getOutgoing()
                                        .stream()
                                        .map(sequenceFlow -> sequenceFlow.getId())
                                        .collect(Collectors.toList()));
                    }
                    if (flowNode.getIncoming().size() > 1) {
                        sequenceFlows
                                .addAll(flowNode.getIncoming()
                                        .stream()
                                        .map(sequenceFlow -> sequenceFlow.getId())
                                        .collect(Collectors.toList()));
                    }
                    outliers.add(new Outlier(flowNode.getId(),sequenceFlows));
                });
        double flowNodesCount = flowNodes.size();
        score = (flowNodesCount - outliers.size())/flowNodesCount;
    }

    public void calculate(Process process) {
        this.process = process;
        calculate();
    }
}
