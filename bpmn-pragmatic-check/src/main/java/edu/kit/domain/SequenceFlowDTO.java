package edu.kit.domain;


import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.di.Waypoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class SequenceFlowDTO {

    String sequenceFlowId;
    List<Waypoint> waypoints;
    Point start;
    Point end;
    String sourceId;
    String targetId;
    List<FlowDirection> flowDirection;


    public SequenceFlowDTO(SequenceFlow sequenceFlow, List<Waypoint> waypoints) {
        this.sequenceFlowId = sequenceFlow.getId();
        this.waypoints = waypoints;
        this.flowDirection = new ArrayList<>();
        int start_x = Integer.parseInt(waypoints.get(0).getAttributeValue("x"));
        int start_y = Integer.parseInt(waypoints.get(0).getAttributeValue("y"));
        int end_x = Integer.parseInt(waypoints.get(waypoints.size() -1).getAttributeValue("x"));
        int end_y = Integer.parseInt(waypoints.get(waypoints.size() -1).getAttributeValue("y"));
        this.start = new Point(start_x,start_y);
        this.end = new Point(end_x,end_y);
        this.sourceId = sequenceFlow.getSource().getId();
        this.targetId = sequenceFlow.getTarget().getId();
        calculateFlowDirection();
    }

    public String getSequenceFlowId() {
        return sequenceFlowId;
    }

    public void calculateFlowDirection() {
        int x = end.x - start.x;
        int y = end.y - start.y;

        if(x > 0) {
            this.flowDirection.add(FlowDirection.RIGHT);
        } else if(x < 0) {
            this.flowDirection.add(FlowDirection.LEFT);
        }
        if(y > 0) {
            this.flowDirection.add(FlowDirection.DOWN);
        } else if (y < 0) {
            this.flowDirection.add(FlowDirection.UP);
            }

    // TODO Overlapping start and end!
    }

    public List<FlowDirection> getFlowDirection() {
        return flowDirection;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }
}
