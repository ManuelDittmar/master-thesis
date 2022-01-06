package edu.kit.domain.quality;

import edu.kit.domain.FlowDirection;
import edu.kit.domain.SequenceFlowDTO;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//TODO: What about loops?

// TODO: just upwards

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
            if (!(sequenceFlowDTO.getFlowDirectionGlobal().contains(FlowDirection.RIGHT)) || sequenceFlowDTO.getArrowHeadDirection().contains(FlowDirection.LEFT)) {
                this.outliers.add(sequenceFlowDTO.getSequenceFlowId());
            }
        }
        setCalculatedScore(sequenceFlowDTOList.size());
    }
}
