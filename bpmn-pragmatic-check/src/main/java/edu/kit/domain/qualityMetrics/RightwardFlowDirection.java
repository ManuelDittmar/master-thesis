package edu.kit.domain.qualityMetrics;

import edu.kit.domain.FlowDirection;
import edu.kit.domain.SequenceFlowDTO;

import java.util.ArrayList;
import java.util.List;

public class RightwardFlowDirection implements QualityCriteria {

    private String criteriaID = "Rightward Flow Direction";
    private List<SequenceFlowDTO> sequenceFlowDTOList;
    private double rightwardFlowDirection;
    private List<SequenceFlowDTO> outliers;

    public RightwardFlowDirection(List<SequenceFlowDTO> sequenceFlowDTOList) {
        this.sequenceFlowDTOList = sequenceFlowDTOList;
        calculate();
    }

    @Override
    public void calculate() {
        double sequenceFlowsCount = sequenceFlowDTOList.size();
        double flowDirectionRightCount = 0;
        List<SequenceFlowDTO> outliers = new ArrayList<>();
        for (SequenceFlowDTO sequenceFlowDTO:this.sequenceFlowDTOList) {
            if(sequenceFlowDTO.getFlowDirection().contains(FlowDirection.RIGHT)) {
                flowDirectionRightCount++;
            }
            else {
                outliers.add(sequenceFlowDTO);
            }
        }
        this.rightwardFlowDirection = flowDirectionRightCount/sequenceFlowsCount;
        this.outliers = outliers;

    }

    public String getCriteriaID() {
        return this.criteriaID;
    }

    @Override
    public double getScore() {
        return this.rightwardFlowDirection;
    }

    @Override
    public List getOutliers() {
        return this.outliers;
    }
}
