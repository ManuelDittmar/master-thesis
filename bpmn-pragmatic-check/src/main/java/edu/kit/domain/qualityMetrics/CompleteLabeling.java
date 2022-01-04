package edu.kit.domain.qualityMetrics;

import java.util.List;
import java.util.stream.Collectors;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value="pragmatic.completeLabeling.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class CompleteLabeling extends QualityCriteria {

    public CompleteLabeling(Process process) {
        super(process);
        calculate();
    }

    public CompleteLabeling() {
        super();
    }

    @Override
    public void calculate() {
        List<FlowElement> elementList = getAllFlowElements(process);
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
