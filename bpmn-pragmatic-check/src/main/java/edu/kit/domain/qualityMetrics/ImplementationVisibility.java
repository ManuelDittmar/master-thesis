package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaExecutionListener;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaTaskListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImplementationVisibility extends QualityCriteria {

    private final Process process;

    public ImplementationVisibility(Process process){
        criteriaID = "Implementation Visibility";
        this.process = process;
        this.outliers = new ArrayList<Outlier>();
        calculate();
    }
    @Override
    public void calculate() {
        List<FlowElement> flowElements = new ArrayList<>(process.getFlowElements());
        if(hasSubProcess(process)){
            flowElements.addAll(getFlowElementsOfSubprocesses(process,List.of(FlowElement.class)));
        }


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
