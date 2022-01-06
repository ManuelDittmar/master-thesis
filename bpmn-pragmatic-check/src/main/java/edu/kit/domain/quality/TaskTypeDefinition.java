package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskTypeDefinition extends TaskTypeDefinitionAbstract {

    // TODO Check Process Execution Conformance
    public TaskTypeDefinition(Process process) {
        super(process);
    }

    public TaskTypeDefinition() {
        super();
    }

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
        double outliersSize =outliers.size();
        score = (flowElementsList.size() - outliersSize)/ flowElementsList.size();
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
            return boundaryEvent.getChildElementsByType(EventDefinition.class).size() == 0;
        }
        return false;
    }

    public boolean hasLoopMarker(BaseElement baseElement) {
        if (baseElement.getElementType().getInstanceType().equals(ServiceTask.class)) {
            ServiceTask serviceTask = (ServiceTask) baseElement;
            if (serviceTask.getDomElement().getChildElements().size() > 0) {
                return serviceTask.getDomElement().getChildElements().get(0).getLocalName().equals("standardLoopCharacteristics");
            }
        }
        return false;
    }

}