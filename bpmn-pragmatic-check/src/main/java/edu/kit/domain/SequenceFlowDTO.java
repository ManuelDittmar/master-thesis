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
    List<FlowDirection> flowDirectionGlobal;
    List<FlowDirection> arrowHeadDirection;


    public SequenceFlowDTO(SequenceFlow sequenceFlow, List<Waypoint> waypoints) {
        this.sequenceFlowId = sequenceFlow.getId();
        this.waypoints = waypoints;
        this.flowDirectionGlobal = new ArrayList<>();
        int start_x = Integer.parseInt(waypoints.get(0).getAttributeValue("x"));
        int start_y = Integer.parseInt(waypoints.get(0).getAttributeValue("y"));
        int precursor_x = Integer.parseInt(waypoints.get(waypoints.size() - 2).getAttributeValue("x"));
        int precursor_y = Integer.parseInt(waypoints.get(waypoints.size() - 2).getAttributeValue("y"));
        int end_x = Integer.parseInt(waypoints.get(waypoints.size() - 1).getAttributeValue("x"));
        int end_y = Integer.parseInt(waypoints.get(waypoints.size() - 1).getAttributeValue("y"));
        this.start = new Point(start_x, start_y);
        this.end = new Point(end_x, end_y);
        this.sourceId = sequenceFlow.getSource().getId();
        this.targetId = sequenceFlow.getTarget().getId();
        flowDirectionGlobal = calculateFlowDirection(start_x, end_x, start_y, end_y);
        arrowHeadDirection = calculateFlowDirection(precursor_x, end_x, precursor_y, end_y);
    }

    public String getSequenceFlowId() {
        return sequenceFlowId;
    }

    public List<FlowDirection> calculateFlowDirection(int start_x, int end_x, int start_y, int end_y) {
        int x = end_x - start_x;
        int y = end_y - start_y;
        List<FlowDirection> list = new ArrayList<>();

        if (x > 0) {
            list.add(FlowDirection.RIGHT);
        } else if (x < 0) {
            list.add(FlowDirection.LEFT);
        }
        if (y > 0) {
            list.add(FlowDirection.DOWN);
        } else if (y < 0) {
            list.add(FlowDirection.UP);
        }

        return list;
        // TODO Overlapping start and end!
    }

    public List<FlowDirection> getFlowDirectionGlobal() {
        return flowDirectionGlobal;
    }

    public List<FlowDirection> getArrowHeadDirection() {
        return arrowHeadDirection;
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
