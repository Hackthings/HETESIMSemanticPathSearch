package main.java.domain.queries;

public class LimitedQuery extends Query {
    private int limit;

    public LimitedQuery(String path, int limit) {
        super(path);
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
