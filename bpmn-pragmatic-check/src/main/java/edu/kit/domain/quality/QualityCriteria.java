package edu.kit.domain.quality;

import edu.kit.domain.common.CriteriaType;

import java.util.ArrayList;
import java.util.List;

public abstract class QualityCriteria {

    String criteriaID;
    CriteriaType criteriaType = CriteriaType.EVALUATIVE;
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

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public void setCalculatedScore(double denominator) {
        if (denominator != 0) {
            score = (denominator - outliers.size()) / denominator;
        } else {
            score = 1;
        }
    }

    public abstract void init();
}
