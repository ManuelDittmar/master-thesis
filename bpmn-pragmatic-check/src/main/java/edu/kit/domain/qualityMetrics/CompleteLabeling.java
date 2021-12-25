package edu.kit.domain.qualityMetrics;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

public class CompleteLabeling extends QualityCriteria{

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

        if(hasSubProcess(process)) {
            elementList.addAll(getFlowElementsOfSubprocesses(process));
        }
        double elementsCount = elementList.size();
        for (FlowElement element:elementList) {
            if (element.getName() == null) {
                // Merging Gateways do not need labeling
                if (element.getElementType().getTypeName().matches("exclusiveGateway|inclusiveGateway")) {
                    Gateway gateway = (Gateway) element;
                    if (gateway.getSucceedingNodes().list().size() > 1) {
                        outliers.add(element.getId());
                    }
                }
                // Sequence Flows that are not coming from a gateway do not need labeling
                if (element.getElementType().getBaseType().getTypeName().equals("flowElement")) {
                    SequenceFlow sequenceFlow = (SequenceFlow) element;
                    String sourceType = sequenceFlow.getSource().getElementType().getTypeName();
                    if (sourceType.matches("exclusiveGateway|inclusiveGateway")) {
                        Gateway gateway = (Gateway) sequenceFlow.getSource();
                        if(gateway.getSucceedingNodes().list().size() > 1 ) {
                            String defaultSequenceFlow = gateway.getAttributeValue("default");
                            if(defaultSequenceFlow == null || !defaultSequenceFlow.equals(element.getId())){
                                System.out.println(gateway.getAttributeValue("default"));
                                outliers.add(element.getId());
                            }
                        }
                    }
                }
                // Tasks and Events need to be labeled always
                if(element.getElementType().getBaseType().getTypeName().matches("activity|task|throwEvent|catchEvent")){
                    outliers.add(element.getId());
                }
            }
        }
        double outliersCount = outliers.size();
        this.score = (elementsCount-outliersCount)/elementsCount;


    }
}
