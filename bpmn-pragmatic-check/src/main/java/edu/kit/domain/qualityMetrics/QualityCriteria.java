package edu.kit.domain.qualityMetrics;

import java.util.ArrayList;
import java.util.List;

public abstract class QualityCriteria {

    String criteriaID;
    double score;
    List outliers;

    public QualityCriteria() {
        criteriaID = this.getClass().getSimpleName();
        outliers = new ArrayList();
    }

    public List getOutliers() {
        return outliers;
    }

    public String getCriteriaID() {
        return criteriaID;
    }

    public double getScore() {
        return score;
    }

    public abstract void calculate();
}
