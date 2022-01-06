package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaExecutionListener;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaTaskListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ImplementationVisibility extends QualityCriteria {

    public ImplementationVisibility(Process process){
        super(process);
    }

    public ImplementationVisibility(){
        super();
    }

    @Override
    public void calculate() {
        List<FlowElement> flowElements = getAllFlowElements(process);

        flowElements.stream().filter(flowElement -> flowElement.getChildElementsByType(ExtensionElements.class).size()> 0)
                .forEach(flowElement -> {
                    Set<String> events = new HashSet<>();
                    flowElement
                            .getChildElementsByType(ExtensionElements.class)
                            .forEach(child -> {
                                child.getChildElementsByType(CamundaExecutionListener.class)
                                        .forEach(listener -> events.add(listener.getCamundaEvent()));
                                if(flowElement.getElementType().getInstanceType().equals(UserTask.class)) {
                                    child.getChildElementsByType(CamundaTaskListener.class)
                                            .forEach(listener -> events.add(listener.getCamundaEvent()));
                                }
                            });
                    outliers.add(new Outlier(flowElement.getId(),events));
                });
        double outliersSize =outliers.size();
        score = (flowElements.size() - outliersSize)/ flowElements.size();
    }
}
