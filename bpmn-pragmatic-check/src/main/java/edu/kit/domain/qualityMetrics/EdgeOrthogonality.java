package edu.kit.domain.qualityMetrics;

import edu.kit.domain.SequenceFlowDTO;
import org.camunda.bpm.model.bpmn.instance.di.Waypoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EdgeOrthogonality implements QualityCriteria{

    private String criteriaID = "Edge Orthogonality";
    private double edgeOrthogonality;
    private List<SequenceFlowDTO> sequenceFlowDTOList;
    private List<String> outliers;

    public EdgeOrthogonality(List<SequenceFlowDTO> sequenceFlowDTOList){
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
        this.edgeOrthogonality = (sequenceFlowDTOList.size()-outliersCount)/sequenceFlowDTOList.size();

    }

    @Override
    public double getScore() {
        return edgeOrthogonality;
    }

    @Override
    public List getOutliers() {
        return outliers;
    }

    @Override
    public String getCriteriaID() {
        return criteriaID;
    }
}
