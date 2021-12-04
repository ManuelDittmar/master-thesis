package edu.kit.domain.qualityMetrics;

import edu.kit.domain.SequenceFlowDTO;
import org.camunda.bpm.model.bpmn.instance.di.Waypoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EdgeOrthogonality extends QualityCriteria{

    private List<SequenceFlowDTO> sequenceFlowDTOList;

    public EdgeOrthogonality(List<SequenceFlowDTO> sequenceFlowDTOList){
        criteriaID = "Edge Orthogonality";
        this.sequenceFlowDTOList = sequenceFlowDTOList;
        this.outliers = new ArrayList<>();
        calculate();
    }
    @Override
    public void calculate() {
        for (SequenceFlowDTO sequenceFlow:sequenceFlowDTOList) {
            for (int i = 0; i < sequenceFlow.getWaypoints().size() -1; i++) {
                Waypoint waypointStart = sequenceFlow.getWaypoints().get(i);
                Waypoint waypointEnd = sequenceFlow.getWaypoints().get(i+1);
                Point startPoint = new Point(Integer.parseInt(waypointStart.getAttributeValue("x")),Integer.parseInt(waypointStart.getAttributeValue("y")));
                Point endPoint = new Point(Integer.parseInt(waypointEnd.getAttributeValue("x")),Integer.parseInt(waypointEnd.getAttributeValue("y")));
                if(startPoint.x != endPoint.x && startPoint.y != endPoint.y ){
                    outliers.add(sequenceFlow.getSequenceFlowId());
                    break;
                }
            }
        }
        double outliersCount = outliers.size();
        this.score = (sequenceFlowDTOList.size()-outliersCount)/sequenceFlowDTOList.size();

    }
}
