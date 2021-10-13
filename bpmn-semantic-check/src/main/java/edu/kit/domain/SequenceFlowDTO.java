package edu.kit.domain;


import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.di.Waypoint;

import java.awt.*;
import java.util.List;


public class SequenceFlowDTO {

    String sequenceFlowId;
    List<Waypoint> waypoints;
    Point start;
    Point end;
    String sourceId;
    String targetId;
    String flowDirection;


    public SequenceFlowDTO(SequenceFlow sequenceFlow, List<Waypoint> waypoints) {
        this.sequenceFlowId = sequenceFlow.getId();
        this.waypoints = waypoints;
        int start_x = Integer.parseInt(waypoints.get(0).getAttributeValue("x"));
        int start_y = Integer.parseInt(waypoints.get(0).getAttributeValue("y"));
        int end_x = Integer.parseInt(waypoints.get(waypoints.size() -1).getAttributeValue("x"));
        int end_y = Integer.parseInt(waypoints.get(waypoints.size() -1).getAttributeValue("y"));
        this.start = new Point(start_x,start_y);
        this.end = new Point(end_x,end_y);
        this.sourceId = sequenceFlow.getSource().getId();
        this.targetId = sequenceFlow.getTarget().getId();
        setFlowDirection();
    }

    public String getSequenceFlowId() {
        return sequenceFlowId;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setFlowDirection() {
        int x = end.x - start.x;
        int y = end.y - start.y;

        if(x > 0) {
            this.flowDirection = "right";
        } else if(x < 0) {
            this.flowDirection = "left";
        }
        else if(x == 0) {
            if(y > 0) {
                this.flowDirection = "down";
            }
            else if (y < 0) {
                this.flowDirection = "up";
            }
        }
    // TODO Overlapping start and end!
    }

    public String getFlowDirection() {
        return flowDirection;
    }
}
