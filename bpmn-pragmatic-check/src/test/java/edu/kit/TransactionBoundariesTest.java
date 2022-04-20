package edu.kit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.kit.domain.common.Outlier;
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
    List<Outlier> outliers;

    @BeforeAll
    public void init() throws FileNotFoundException {
        ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\TransactionBoundariesTest.bpmn"));
        List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
        transactionBoundaries = new TransactionBoundaries(processList.get(0));
        outliers = transactionBoundaries.getOutliers();
    }

    @Test
    public void hasOutlierStartEvent() {
        long outlierCount = outliers.stream().filter(outlier -> outlier.getId().contains("NoBoundaryStartEvent")).count();
        assertEquals(1,outlierCount);
    }

    @Test
    public void hasOutlierAsyncBeforeJoiningParallelGateway(){
        long outlierCount = outliers.stream().filter(outlier -> outlier.getId().contains("AsyncBeforeJoiningParallelGateway")).count();
        assertEquals(1,outlierCount);
    }

    @Test
    public void hasOutlierWaitMessageTask(){
        long outlierCount = outliers.stream().filter(outlier -> outlier.getId().contains("WaitMessageTask")).count();
        assertEquals(1,outlierCount);
    }
}
