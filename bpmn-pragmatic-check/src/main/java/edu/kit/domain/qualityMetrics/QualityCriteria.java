package edu.kit.domain.qualityMetrics;

import java.util.List;

public interface QualityCriteria {
    
    String criteriaID = null;
    
    public void calculate();
    public double getScore();
    public List getOutliers();
}
