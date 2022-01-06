package edu.kit.domain.quality;

import org.camunda.bpm.model.xml.ModelInstance;

public abstract class DiagramQualityCriteria extends QualityCriteria {

    ModelInstance modelInstance;

    public DiagramQualityCriteria() {

    }

    public DiagramQualityCriteria(ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
        init();
    }

    public <T extends DiagramQualityCriteria> T createInstance(ModelInstance modelInstance) {
        try {
            return (T) this.getClass().getConstructor(ModelInstance.class).newInstance(modelInstance);
        } catch (Exception e) {
            throw new RuntimeException("Could not create Instance of " + this, e);
        }
    }
}
