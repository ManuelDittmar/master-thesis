package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = "pragmatic.sourcesAndSinks.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class SourcesAndSinks extends SourcesAndSinksAbstract {

    public SourcesAndSinks() {
        super();
    }

    public SourcesAndSinks(Process process) {
        super(process);
    }

    @Override
    public void init() {
        int startEventCount = process.getChildElementsByType(StartEvent.class).size();
        int endEventCount = process.getChildElementsByType(EndEvent.class).size();
        boolean tooManyStartEvents = startEventCount > maxStartEvents;
        boolean tooManyEndEvents = endEventCount > maxEndEvents;

        if (tooManyStartEvents) {
            outliers.add("StartEvents > Max StartEvents (" + maxStartEvents + ")");
        }

        if (tooManyEndEvents) {
            outliers.add("EndEvents > Max EndEvents (" + maxEndEvents + ")");
        }

        if (tooManyStartEvents && tooManyEndEvents) {
            score = 0;
        } else if (tooManyStartEvents || tooManyEndEvents) {
            score = 0.5;
        } else {
            score = 1;
        }
    }
}