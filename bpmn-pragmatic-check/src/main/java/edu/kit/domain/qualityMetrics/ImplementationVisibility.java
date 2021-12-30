package edu.kit.domain.qualityMetrics;

import edu.kit.domain.SequenceFlowDTO;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;
import java.util.List;

public class ImplementationVisibility extends QualityCriteria {

    private final Process process;
    private final List<SequenceFlowDTO> sequenceFlowDTOList;

    public ImplementationVisibility(Process process,List<SequenceFlowDTO> sequenceFlowDTOList){
        criteriaID = "Implementation Visibility";
        this.process = process;
        this.sequenceFlowDTOList = sequenceFlowDTOList;
        this.outliers = new ArrayList<>();
        calculate();
    }
    @Override
    public void calculate() {

    }
}
