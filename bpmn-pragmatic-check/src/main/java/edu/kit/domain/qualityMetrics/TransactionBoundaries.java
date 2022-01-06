package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class TransactionBoundaries extends ProcessQualityCriteria {

    public TransactionBoundaries(Process process) {
        super(process);
    }

    public TransactionBoundaries() {
        super();
    }

    @Override
    public void calculate() {
        List<FlowElement> flowElements = getAllFlowElements(process);
        flowElements.forEach(flowElement -> {
                    Class<? extends ModelElementInstance> instanceType = flowElement.getElementType().getInstanceType();
                    instanceType.cast(flowElement);
                    boolean hasCorrectBoundaries = true;
                    String reason = null;
                    if (instanceType.equals(StartEvent.class)) {
                        hasCorrectBoundaries = hasCorrectBoundaries((StartEvent) flowElement);
                        reason = "Add Transaction Boundary Before. Ensure persistence of process instance";
                    }

                    if (isNaturalWaitState(instanceType)) {
                        hasCorrectBoundaries = hasCorrectBoundaries((FlowNode) flowElement);
                        reason = "Remove Transaction Boundary Before OR Add Transaction Boundary After. Avoid Overhead, due to natural wait state";
                    }
                    if (instanceType.equals(ExclusiveGateway.class)) {
                        hasCorrectBoundaries = hasCorrectBoundaries((ExclusiveGateway) flowElement);
                        reason = "Avoid Overhead, no error prone operation";
                    }

                    if (instanceType.equals(ParallelGateway.class)) {
                        hasCorrectBoundaries = hasCorrectBoundaries((ParallelGateway) flowElement);
                        reason = "Add Transaction Boundary Before. Optimistic locking exception";
                    }

                    if (instanceType.equals(InclusiveGateway.class)) {
                        hasCorrectBoundaries = hasCorrectBoundaries((InclusiveGateway) flowElement);
                        reason = "Add Transaction Boundary Before. Optimistic locking exception";
                    }

                    // TODO Optimize Code

                    if (!hasCorrectBoundaries) {
                        // System.out.println(flowElement.getId() + " : has wrong TransactionBoundaries");
                        outliers.add(new Outlier(flowElement.getId(), Set.of(reason)));
                    }

                });
        double outlierCount = outliers.size();
        score = (flowElements.size() - outlierCount) / flowElements.size();
    }

    // TODO Event based gateway
    public boolean isNaturalWaitState(Class<? extends ModelElementInstance> instanceType) {
        return instanceType.equals(UserTask.class) || instanceType.equals(IntermediateCatchEvent.class) || instanceType.equals(ReceiveTask.class);
    }

    public boolean hasCorrectBoundaries(StartEvent startEvent) {
        return (startEvent.isCamundaAsyncBefore() || startEvent.isCamundaAsyncAfter());
    }

    public boolean hasCorrectBoundaries(ExclusiveGateway exclusiveGateway) {
        return (!exclusiveGateway.isCamundaAsyncBefore() && !exclusiveGateway.isCamundaAsyncAfter());
    }

    public boolean hasCorrectBoundaries(FlowNode flowNode) {
        return flowNode.isCamundaAsyncAfter() && !flowNode.isCamundaAsyncBefore();
    }

    public boolean hasCorrectBoundaries(ParallelGateway parallelGateway) {
        if (isMergingGateway(parallelGateway)) {
            return parallelGateway.isCamundaAsyncBefore() && !parallelGateway.isCamundaAsyncAfter();
        }
        return !parallelGateway.isCamundaAsyncAfter() && !parallelGateway.isCamundaAsyncBefore();
    }

    public boolean hasCorrectBoundaries(InclusiveGateway inclusiveGateway) {
        if (isMergingGateway(inclusiveGateway)) {
            return inclusiveGateway.isCamundaAsyncBefore() && !inclusiveGateway.isCamundaAsyncAfter();
        }
        return !inclusiveGateway.isCamundaAsyncAfter() && !inclusiveGateway.isCamundaAsyncBefore();
    }


}
