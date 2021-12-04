package edu.kit.domain.qualityMetrics;

import java.util.List;

public abstract class QualityCriteria {
    
    String criteriaID;
    double score;
    List outliers;
    
    public abstract void calculate();
    public List getOutliers() {
        return outliers;
    };
    public String getCriteriaID() {
        return criteriaID;
    }

    public double getScore() {
        return score;
    };

}
