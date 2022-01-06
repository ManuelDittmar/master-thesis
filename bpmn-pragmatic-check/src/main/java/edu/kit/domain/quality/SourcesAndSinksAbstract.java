package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.beans.factory.annotation.Value;

public abstract class SourcesAndSinksAbstract extends ConfigurableProcessQualityCriteria {

    @Value("${pragmatic.sourcesAndSinks.maxStartEvents:2}")
    public int maxStartEvents;
    @Value("${pragmatic.sourcesAndSinks.maxEndEvents:4}")
    public int maxEndEvents;

    public SourcesAndSinksAbstract() {
    }

    public SourcesAndSinksAbstract(Process process) {
        super(process);
    }

    @Override
    public <T extends ProcessQualityCriteria> T createInstance(Process process) {
        SourcesAndSinksAbstract qualityCriteria =  super.createInstance(process);
        qualityCriteria.setMaxStartEvents(this.maxStartEvents);
        qualityCriteria.setMaxEndEvents(this.maxEndEvents);
        qualityCriteria.calculate();
        return (T) qualityCriteria;
    }

    public void setMaxStartEvents(int maxStartEvents) {
        this.maxStartEvents = maxStartEvents;
    }

    public void setMaxEndEvents(int maxEndEvents) {
        this.maxEndEvents = maxEndEvents;
    }
}
