package edu.kit.domain.quality;

import edu.kit.domain.FlowDirection;
import edu.kit.domain.SequenceFlowDTO;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Gateway;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RightwardFlowDirection extends FlowProcessQualityCriteria {

    public RightwardFlowDirection(Process process) {
        super(process);
    }

    public RightwardFlowDirection() {
        super();
    }

    @Override
    public void init() {
        this.sequenceFlowDTOList = initSequenceFlowDTOs();
        for (SequenceFlowDTO sequenceFlowDTO : this.sequenceFlowDTOList) {
            if ( isOutlier(sequenceFlowDTO)) {
                this.outliers.add(sequenceFlowDTO.getSequenceFlowId());
            }
        }
        setCalculatedScore(sequenceFlowDTOList.size());
    }

    boolean isOutlier(SequenceFlowDTO sequenceFlow) {
        List<FlowNode> previousNodes = getAllPreviousFlowNodes(sequenceFlow.getSource());
        // Loop
        if(previousNodes.contains(sequenceFlow.getTarget())) {
            return false;
        }

        if ( sequenceFlow.getArrowHeadDirection().contains(FlowDirection.LEFT)) {
            return true;
        }
        if(!sequenceFlow.getSource().getElementType().getBaseType().getInstanceType().equals(Gateway.class) && !sequenceFlow.getArrowHeadDirection().contains(FlowDirection.RIGHT)) {
            return true;
        }
        return false;
    }
}
