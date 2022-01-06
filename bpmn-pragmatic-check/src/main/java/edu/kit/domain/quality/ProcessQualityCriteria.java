package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.BaseElement;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.Gateway;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SubProcess;

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

}
