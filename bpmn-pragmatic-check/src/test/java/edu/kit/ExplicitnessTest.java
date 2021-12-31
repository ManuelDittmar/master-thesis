package edu.kit;

import edu.kit.domain.qualityMetrics.Explicitness;
import edu.kit.domain.qualityMetrics.TaskTypeDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExplicitnessTest {

    String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
    Explicitness explicitness;

    @BeforeAll
    public void init() throws FileNotFoundException {
        ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\ExplicitnessTest.bpmn"));
        List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
        explicitness = new Explicitness(processList.get(0));
    }

    @Test
    public void hasOutlierNoneTaskType() {
    }
}
