package edu.kit.domain.qualityMetrics;

import edu.kit.domain.FlowDirection;
import edu.kit.domain.SequenceFlowDTO;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;
import java.util.List;

//TODO: What about loops?

// TODO: just upwards

public class RightwardFlowDirection extends FlowQualityCriteria {

    public RightwardFlowDirection(Process process) {
        super(process);
        calculate();
    }

    @Override
    public void calculate() {
        double sequenceFlowsCount = sequenceFlowDTOList.size();
        double outliersCount = 0;
        List<String> outliers = new ArrayList<>();
        for (SequenceFlowDTO sequenceFlowDTO:this.sequenceFlowDTOList) {
            if(!(sequenceFlowDTO.getFlowDirectionGlobal().contains(FlowDirection.RIGHT)) || sequenceFlowDTO.getArrowHeadDirection().contains(FlowDirection.LEFT)){
                outliersCount++;
                outliers.add(sequenceFlowDTO.getSequenceFlowId());
            }
        }
        this.score = (sequenceFlowsCount -outliersCount)/sequenceFlowsCount;
        this.outliers = outliers;
    }
}
