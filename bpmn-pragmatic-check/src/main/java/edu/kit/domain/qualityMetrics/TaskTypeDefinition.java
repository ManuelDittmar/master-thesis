package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.di.DiagramElement;
import org.camunda.bpm.model.xml.instance.DomElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TaskTypeDefinition extends QualityCriteria {

    Process process;
    List<String> forbiddenTaskTypes;

    // TODO allow to modify allowedSymbols
    public TaskTypeDefinition(Process process) {
        criteriaID = "Task Type Definition";
        this.process = process;
        outliers = new ArrayList<String>();
        forbiddenTaskTypes = List.of("manualTask", "task", "scriptTask");
        calculate();
    }

    // TODO difference between expanded and non-expanded subprocess

    // TODO Boundary Event cannot be non-typed

    // TODO when task is in subprocess, also needs to be checked

    // TODO Loop marker not allowed

    @Override
    public void calculate() {
        List<FlowElement> flowElementsList = new ArrayList<>();
        flowElementsList.addAll(process.getChildElementsByType(Activity.class));
        flowElementsList.addAll(process.getChildElementsByType(Event.class));
        flowElementsList.addAll(getFlowElementsOfSubprocesses(process, List.of(Event.class, Activity.class)));
        outliers = flowElementsList.stream()
                .filter(element -> forbiddenTaskTypes.contains(element.getElementType().getTypeName()) || isEmptySubprocess(element) || isNonTypedBoundaryEvent(element) || hasLoopMarker(element))
                .map(element -> element.getId())
                .collect(Collectors.toList());
    }

    public boolean isEmptySubprocess(BaseElement baseElement) {
        if (baseElement.getElementType().getInstanceType().equals(SubProcess.class)) {
            SubProcess subProcess = (SubProcess) baseElement;
            return subProcess.getRawTextContent().equals("");
        }
        return false;
    }

    public boolean isNonTypedBoundaryEvent(BaseElement baseElement) {
        if (baseElement.getElementType().getInstanceType().equals(BoundaryEvent.class)) {
            BoundaryEvent boundaryEvent = (BoundaryEvent) baseElement;
            return boundaryEvent.getRawTextContent().equals("");
        }
        return false;
    }

    public boolean hasLoopMarker(BaseElement baseElement) {
        if (baseElement.getElementType().getInstanceType().equals(ServiceTask.class)) {
            ServiceTask serviceTask = (ServiceTask) baseElement;
            if (serviceTask.getDomElement().getChildElements().size() > 0) {
                if (serviceTask.getDomElement().getChildElements().get(0).getLocalName().equals("standardLoopCharacteristics")) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getForbiddenTaskTypes() {
        return forbiddenTaskTypes;
    }

    public void setForbiddenTaskTypes(List<String> forbiddenTaskTypes) {
        this.forbiddenTaskTypes = forbiddenTaskTypes;
    }
}
