package main.java.ownClasses.domain.queries;

public class IntervaledQuery extends Query {
    private double firstRelevance;
    private double secondRelevance;

    public IntervaledQuery(String path, double firstRelevance, double secondRelevance) {
        super(path);
        this.firstRelevance = firstRelevance;
        this.secondRelevance = secondRelevance;
    }

    public double getFirstRelevance() {
        return firstRelevance;
    }

    public void setFirstRelevance(double firstRelevance) {
        this.firstRelevance = firstRelevance;
    }

    public double getSecondRelevance() {
        return secondRelevance;
    }

    public void setSecondRelevance(double secondRelevance) {
        this.secondRelevance = secondRelevance;
    }
}
