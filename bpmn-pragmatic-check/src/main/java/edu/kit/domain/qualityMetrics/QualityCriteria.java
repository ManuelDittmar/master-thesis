package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.BaseElement;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.SubProcess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class QualityCriteria {
    
    String criteriaID;
    double score;
    List outliers;

    public abstract void calculate();

    public List getOutliers() {
        return outliers;
    }

    public String getCriteriaID() {
        return criteriaID;
    }

    public double getScore() {
        return score;
    }

    public boolean hasSubProcess(BaseElement baseElement) {
        return baseElement.getChildElementsByType(SubProcess.class).size() > 0;
    }

    public Collection<FlowElement> getFlowElementsOfSubprocesses(BaseElement baseElement) {
        Collection<FlowElement> flowElements = new ArrayList<>();
        baseElement.getChildElementsByType(SubProcess.class)
                .forEach(subProcess -> {
                    flowElements.addAll(subProcess.getChildElementsByType(FlowElement.class));
                    if (hasSubProcess(subProcess)){
                        flowElements.addAll(getFlowElementsOfSubprocesses(subProcess));
                    }
                });
        return flowElements;
    }

}
