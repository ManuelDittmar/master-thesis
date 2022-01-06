package edu.kit.domain.qualityMetrics;

import edu.kit.domain.SequenceFlowDTO;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.di.Waypoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Line2D;

@Component
@ConditionalOnProperty(
        value="pragmatic.edgeCrossing.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class EdgeCrossing extends FlowQualityCriteria{

    public EdgeCrossing() {
        super();
    }

    public EdgeCrossing(Process process){
        super(process);
    }

    @Override
    public void calculate() {
        this.sequenceFlowDTOList = initSequenceFlowDTOs();
        for (int i = 0; i < sequenceFlowDTOList.size(); i++) {
            SequenceFlowDTO sequenceFlowA = sequenceFlowDTOList.get(i);
            for (int j = i+1; j < sequenceFlowDTOList.size() ; j++) {
                SequenceFlowDTO sequenceFlowB = sequenceFlowDTOList.get(j);
                boolean crossingFound = false;
                for (int k = 0; k < sequenceFlowA.getWaypoints().size() -1; k++) {
                    if(!crossingFound) {
                        Waypoint waypointStartA = sequenceFlowA.getWaypoints().get(k);
                        Waypoint waypointEndA = sequenceFlowA.getWaypoints().get(k+1);
                        Point startLineA = new Point(Integer.parseInt(waypointStartA.getAttributeValue("x")),Integer.parseInt(waypointStartA.getAttributeValue("y")));
                        Point endLineA = new Point(Integer.parseInt(waypointEndA.getAttributeValue("x")),Integer.parseInt(waypointEndA.getAttributeValue("y")));
                        Line2D line1 = new Line2D.Float(startLineA.x,startLineA.y,endLineA.x,endLineA.y);
                        for (int l = 0; l < sequenceFlowB.getWaypoints().size() -1; l++) {
                            Waypoint waypointStartB = sequenceFlowB.getWaypoints().get(l);
                            Waypoint waypointEndB = sequenceFlowB.getWaypoints().get(l+1);
                            Point startLineB = new Point(Integer.parseInt(waypointStartB.getAttributeValue("x")),Integer.parseInt(waypointStartB.getAttributeValue("y")));
                            Point endLineB = new Point(Integer.parseInt(waypointEndB.getAttributeValue("x")),Integer.parseInt(waypointEndB.getAttributeValue("y")));
                            Line2D line2 = new Line2D.Float(startLineB.x,startLineB.y,endLineB.x,endLineB.y);
                            Boolean intersect = line1.intersectsLine(line2);
                            //  on top of each other
                            double slopeLine1;
                            if(endLineA.x != startLineA.x){
                                slopeLine1 = (endLineA.y - startLineA.y)/ (endLineA.x - startLineA.x);
                            }
                            else{
                                slopeLine1 = Double.POSITIVE_INFINITY;
                            }
                            double slopeLine2;
                            if(endLineB.x != startLineB.x){
                                slopeLine2 = (endLineB.y - startLineB.y)/ (endLineB.x - startLineB.x);
                            }
                            else{
                                slopeLine2 = Double.POSITIVE_INFINITY;
                            }
                            boolean parallel = slopeLine1 == slopeLine2;
                            if(intersect && !parallel){
                                Point intersectionPoint = lineLineIntersection(startLineA,endLineA,startLineB,endLineB);
                                if((intersectionPoint.x == startLineA.x && intersectionPoint.y == startLineA.y) || (intersectionPoint.x == endLineA.x && intersectionPoint.y == endLineA.y)  || (intersectionPoint.x == startLineB.x && intersectionPoint.y == startLineB.y)|| (intersectionPoint.x == endLineB.x && intersectionPoint.y == endLineB.y)){
                                     // They just touch each other in one of the starting points;
                                    }
                                else {
                                    String[] pair = new String[2];
                                    pair[0] = sequenceFlowA.getSequenceFlowId();
                                    pair[1] = sequenceFlowB.getSequenceFlowId();
                                    outliers.add(pair);
                                    crossingFound = true;
                                    break;
                                }
                                }
                            }
                        }
                        else {
                                break;
                    }
                    }
                }

            }
            double outliersCount = outliers.size();
            this.score = (sequenceFlowDTOList.size()-outliersCount)/sequenceFlowDTOList.size();
        }

    static Point lineLineIntersection(Point A, Point B, Point C, Point D)
    {
        // Line AB represented as a1x + b1y = c1
        double a1 = B.y - A.y;
        double b1 = A.x - B.x;
        double c1 = a1*(A.x) + b1*(A.y);

        // Line CD represented as a2x + b2y = c2
        double a2 = D.y - C.y;
        double b2 = C.x - D.x;
        double c2 = a2*(C.x)+ b2*(C.y);

        double determinant = a1*b2 - a2*b1;

        if (determinant == 0)
        {
            // The lines are parallel. This is simplified
            // by returning a pair of FLT_MAX
            return new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        else
        {
            int x = (int) ((b2*c1 - b1*c2)/determinant);
            int y = (int) ((a1*c2 - a2*c1)/determinant);
            return new Point(x, y);
        }
    }
}
