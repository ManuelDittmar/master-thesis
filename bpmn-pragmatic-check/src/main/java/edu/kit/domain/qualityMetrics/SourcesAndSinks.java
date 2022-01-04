package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value="pragmatic.sourcesAndSinks.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class SourcesAndSinks extends QualityCriteria {

    @Value("${pragmatic.sourcesAndSinks.maxStartEvents:2}")
    int maxStartEvents;
    @Value("${pragmatic.sourcesAndSinks.maxEndEvents:4}")
    int maxEndEvents;

    public SourcesAndSinks() {
        super();
    }
    @Override
    public void calculate() {
        int startEventCount = process.getChildElementsByType(StartEvent.class).size();
        int endEventCount = process.getChildElementsByType(EndEvent.class).size();
        boolean tooManyStartEvents = startEventCount > maxStartEvents;
        boolean tooManyEndEvents = endEventCount > maxEndEvents;

        if (tooManyStartEvents) {
            outliers.add("StartEvents > Max StartEvents (" + maxStartEvents + ")");
        }

        if(tooManyEndEvents) {
            outliers.add("EndEvents > Max EndEvents (" + maxEndEvents + ")");
        }

        if(tooManyStartEvents && tooManyEndEvents) {
            score = 0;
        } else if(tooManyStartEvents || tooManyEndEvents) {
            score = 0.5;
        } else {
            score = 1;
        }

    }
}