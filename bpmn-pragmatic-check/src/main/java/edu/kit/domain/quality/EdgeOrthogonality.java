package edu.kit.domain.quality;

import edu.kit.domain.SequenceFlowDTO;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.di.Waypoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@ConditionalOnProperty(
        value = "pragmatic.edgeOrthogonality.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class EdgeOrthogonality extends FlowProcessQualityCriteria {


    public EdgeOrthogonality() {
        super();
    }

    public EdgeOrthogonality(Process process) {
        super(process);
    }

    @Override
    public void init() {
        this.sequenceFlowDTOList = initSequenceFlowDTOs();
        for (SequenceFlowDTO sequenceFlow : sequenceFlowDTOList) {
            for (int i = 0; i < sequenceFlow.getWaypoints().size() - 1; i++) {
                Waypoint waypointStart = sequenceFlow.getWaypoints().get(i);
                Waypoint waypointEnd = sequenceFlow.getWaypoints().get(i + 1);
                Point startPoint = new Point(Integer.parseInt(waypointStart.getAttributeValue("x")), Integer.parseInt(waypointStart.getAttributeValue("y")));
                Point endPoint = new Point(Integer.parseInt(waypointEnd.getAttributeValue("x")), Integer.parseInt(waypointEnd.getAttributeValue("y")));
                if (startPoint.x != endPoint.x && startPoint.y != endPoint.y) {
                    outliers.add(sequenceFlow.getSequenceFlowId());
                    break;
                }
            }
        }
        setCalculatedScore(sequenceFlowDTOList.size());
    }
}
