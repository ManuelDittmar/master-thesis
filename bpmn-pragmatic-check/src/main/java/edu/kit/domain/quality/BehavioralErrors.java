package edu.kit.domain.quality;

import edu.kit.domain.Outlier;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.ParallelGateway;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class BehavioralErrors extends ProcessQualityCriteria {

    public BehavioralErrors() {
    }

    public BehavioralErrors(Process process) {
        super(process);
    }


    @Override
    public void init() {
        // Synchronizing Gateway
        List<ParallelGateway> parallelGatewayList = new ArrayList<>(process.getChildElementsByType(ParallelGateway.class)).stream()
                .filter(parallelGateway -> parallelGateway.getIncoming().size()>1).collect(Collectors.toList());
        parallelGatewayList.forEach(parallelGateway -> {
            int incomingSequenceFlows = parallelGateway.getIncoming().size();
            boolean foundSplitting = false;
            long parallelSplits = getAllPreviousFlowNodes(parallelGateway).stream()
                    .filter(flowNode -> hasSplittingBehavior(flowNode,incomingSequenceFlows)).count();
            if(parallelSplits > 0) {
                foundSplitting = true;
            }
            if(!foundSplitting) {
                outliers.add(new Outlier(parallelGateway.getId(), Set.of("Potential Deadlock")));
            }
        });
        setCalculatedScore(parallelGatewayList.size());
    }

    public boolean hasSplittingBehavior(FlowNode flowNode,int incomingFlows) {
        if(flowNode.getOutgoing().size() < incomingFlows) {
            return false;
        }
        boolean isSplittingActivity = flowNode.getElementType().getBaseType().getTypeName().equals("activity");
        boolean isSplittingTask =  flowNode.getElementType().getBaseType().getTypeName().equals("task");
        boolean isParallelGateway = flowNode.getElementType().getInstanceType().getSimpleName().equals("ParallelGateway");
        return (isSplittingActivity || isSplittingTask || isParallelGateway);
    }

}
