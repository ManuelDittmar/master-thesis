package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ProcessQualityCriteria extends QualityCriteria {

    Process process;

    public ProcessQualityCriteria() {
        super();
    }

    public ProcessQualityCriteria(Process process) {
        super();
        this.process = process;
        init();
    }

    public <T extends ProcessQualityCriteria> T createInstance(Process process) {
        try {
            return (T) this.getClass().getConstructor(Process.class).newInstance(process);
        } catch (Exception e) {
            throw new RuntimeException("Could not create Instance of " + this, e);
        }

    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public boolean hasSubProcess(BaseElement baseElement) {
        return baseElement.getChildElementsByType(SubProcess.class).size() > 0;
    }

    public Collection<FlowElement> getFlowElementsOfSubprocesses(BaseElement baseElement, Collection<Class> classes) {
        Collection<FlowElement> flowElements = new ArrayList<>();
        baseElement.getChildElementsByType(SubProcess.class)
                .forEach(subProcess -> {
                    for (Class aClass : classes) {
                        flowElements.addAll(subProcess.getChildElementsByType(aClass));
                    }
                    if (hasSubProcess(subProcess)) {
                        flowElements.addAll(getFlowElementsOfSubprocesses(subProcess, classes));
                    }
                });
        return flowElements;
    }

    public boolean isMergingGateway(BaseElement element) {
        if (element.getElementType().getTypeName().matches("exclusiveGateway|inclusiveGateway")) {
            Gateway gateway = (Gateway) element;
            return (gateway.getSucceedingNodes().list().size() > 1);
        } else {
            return false;
        }
    }

    public List<FlowElement> getAllFlowElements(Process process) {
        List<FlowElement> flowElements = new ArrayList<>(process.getFlowElements());
        if (hasSubProcess(process)) {
            flowElements.addAll(getFlowElementsOfSubprocesses(process, List.of(FlowElement.class)));
        }
        return flowElements;
    }

    public boolean needsLabel(BaseElement element) {
        return isMergingGateway(element) || sequenceFlowNeedsLabel(element) || needsToBeLabeledBasedOnType(element);
    }

    public boolean needsToBeLabeledBasedOnType(BaseElement element) {
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
                    return defaultSequenceFlow == null || !defaultSequenceFlow.equals(element.getId());
                }
            }
        }
        return false;
    }

}
