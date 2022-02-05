package edu.kit.domain.quality;

import java.util.List;
import java.util.stream.Collectors;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = "pragmatic.completeLabeling.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class CompleteLabeling extends ProcessQualityCriteria {

    // TODO Complete Labeling Pools and Lanes, maybe a Diagram level criteria?

    public CompleteLabeling(Process process) {
        super(process);
    }

    public CompleteLabeling() {
        super();
    }

    @Override
    public void init() {
        List<FlowElement> elementList = getAllFlowElements(process);
        double elementsCount = elementList.size();
        outliers = elementList.stream().filter(element -> element.getName() == null)
                .filter(element -> needsLabel(element))
                .map(element -> element.getId())
                .collect(Collectors.toList());
        setCalculatedScore(elementsCount);
    }

}
