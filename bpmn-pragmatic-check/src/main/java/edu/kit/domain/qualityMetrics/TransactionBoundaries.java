package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.util.ArrayList;
import java.util.List;

public class TransactionBoundaries extends QualityCriteria{

    public TransactionBoundaries(Process process){
        super(process);
        calculate();
    }

    @Override
    public void calculate() {
        List<FlowElement> flowElements = getAllFlowElements(process);
        flowElements.forEach(flowElement -> {
            Class<? extends ModelElementInstance> instanceType = flowElement.getElementType().getInstanceType();
            System.out.println(instanceType);
            instanceType.cast(flowElement);
            boolean hasCorrectBoundaries = true;
            if(instanceType.equals(StartEvent.class)){
                hasCorrectBoundaries = hasCorrectBoundaries((StartEvent) flowElement);
            }

            if(isNaturalWaitState(instanceType)){
                hasCorrectBoundaries = hasCorrectBoundaries((FlowNode) flowElement);
            }
            if(instanceType.equals(ExclusiveGateway.class)){
                hasCorrectBoundaries = hasCorrectBoundaries((ExclusiveGateway) flowElement);
            }

            if(instanceType.equals(ParallelGateway.class)){
                hasCorrectBoundaries = hasCorrectBoundaries((ParallelGateway) flowElement);
            }

            if(instanceType.equals(InclusiveGateway.class)){
                hasCorrectBoundaries = hasCorrectBoundaries((InclusiveGateway) flowElement);
            }

            if(!hasCorrectBoundaries){
                System.out.println(flowElement.getId() + " : has wrong TransactionBoundaries");
            }

        });
    }

    // TODO Event based gateway
    public boolean isNaturalWaitState(Class<? extends ModelElementInstance> instanceType) {
        return instanceType.equals(UserTask.class) || instanceType.equals(IntermediateCatchEvent.class) || instanceType.equals(ReceiveTask.class);
    }

    public boolean hasCorrectBoundaries(StartEvent startEvent){
        return (startEvent.isCamundaAsyncBefore() || startEvent.isCamundaAsyncAfter());
    }

    public boolean hasCorrectBoundaries(ExclusiveGateway exclusiveGateway){
        return (!exclusiveGateway.isCamundaAsyncBefore() && !exclusiveGateway.isCamundaAsyncAfter());
    }

    public boolean hasCorrectBoundaries(FlowNode flowNode){
        return flowNode.isCamundaAsyncAfter() && !flowNode.isCamundaAsyncBefore();
    }

    public boolean hasCorrectBoundaries(ParallelGateway parallelGateway){
        if(isMergingGateway(parallelGateway)){
            return parallelGateway.isCamundaAsyncBefore() && !parallelGateway.isCamundaAsyncAfter();
        }
        return !parallelGateway.isCamundaAsyncAfter() && !parallelGateway.isCamundaAsyncBefore();
    }

    public boolean hasCorrectBoundaries(InclusiveGateway inclusiveGateway){
        if(isMergingGateway(inclusiveGateway)){
            return inclusiveGateway.isCamundaAsyncBefore() && !inclusiveGateway.isCamundaAsyncAfter();
        }
        return !inclusiveGateway.isCamundaAsyncAfter() && !inclusiveGateway.isCamundaAsyncBefore();
    }

    public boolean hasCorrectBoundaries(Task task){
        return !task.isCamundaAsyncAfter() && !task.isCamundaAsyncBefore();
    }





}
