package edu.kit;

import edu.kit.domain.quality.TransactionBoundaries;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionBoundariesTest {

    String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
    TransactionBoundaries transactionBoundaries;

    @BeforeAll
    public void init() throws FileNotFoundException {
        ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\TransactionBoundariesTest.bpmn"));
        List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
        transactionBoundaries = new TransactionBoundaries(processList.get(0));
    }

    @Test
    public void hasOutlierStartEvent() {

    }
}
