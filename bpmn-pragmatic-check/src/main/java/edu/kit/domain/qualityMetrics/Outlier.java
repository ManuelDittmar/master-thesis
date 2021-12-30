package edu.kit.domain.qualityMetrics;

import java.util.Set;

public class Outlier {
    private String id;
    private Set reasons;

    public Outlier(String id, Set<String> reasons) {
        this.id = id;
        this.reasons = reasons;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set getReasons() {
        return reasons;
    }

    public void setReasons(Set reasons) {
        this.reasons = reasons;
    }
}
