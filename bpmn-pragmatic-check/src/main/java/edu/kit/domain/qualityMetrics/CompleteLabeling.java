package edu.kit.domain.qualityMetrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

public class CompleteLabeling extends QualityCriteria {

    Process process;

    public CompleteLabeling(Process process) {
        criteriaID = "Complete Labeling";
        this.process = process;
        outliers = new ArrayList<String>();
        calculate();
    }

    // TODO does a subprocess need to be labeled?

    @Override
    public void calculate() {
        List<FlowElement> elementList = new ArrayList<>();
        elementList.addAll(process.getFlowElements());
        if (hasSubProcess(process)) {
            getFlowElementsOfSubprocesses(process, List.of(FlowElement.class));
            elementList.addAll(getFlowElementsOfSubprocesses(process, List.of(FlowElement.class)));
        }
        double elementsCount = elementList.size();
        outliers = elementList.stream().filter(element -> element.getName() == null)
                .filter(element -> needsLabel(element))
                .map(element -> element.getId())
                .collect(Collectors.toList());
        double outliersCount = outliers.size();
        this.score = (elementsCount - outliersCount) / elementsCount;
    }

    public boolean needsLabel(BaseElement element){
        return isMergingGateway(element ) || sequenceFlowNeedsLabel(element) || needsToBeLabeledBasedOnType(element);
    }

    public boolean needsToBeLabeledBasedOnType(BaseElement element){
        return element.getElementType().getBaseType().getTypeName().matches("activity|task|throwEvent|catchEvent")
                && !element.getElementType().getInstanceType().equals(SubProcess.class);
    }

    public boolean sequenceFlowNeedsLabel(BaseElement element) {
        if (element.getElementType().getInstanceType().equals(SequenceFlow.class)) {
            SequenceFlow sequenceFlow = (SequenceFlow) element;
            String sourceType = sequenceFlow.getSource().getElementType().getTypeName();
            if (sourceType.matches("exclusiveGateway|inclusiveGateway")) {
                Gateway gateway = (Gateway) sequenceFlow.getSource();
                if (gateway.getSucceedingNodes().list().size() > 1) {
                    String defaultSequenceFlow = gateway.getAttributeValue("default");
                    // default sequence flow needs no label
                    if (defaultSequenceFlow == null || !defaultSequenceFlow.equals(element.getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
