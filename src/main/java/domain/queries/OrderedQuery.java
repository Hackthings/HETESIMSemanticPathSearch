package main.java.domain.queries;

public class OrderedQuery extends Query {
    private boolean upward;

    public OrderedQuery(String path, boolean upward) {
        super(path);
        this.upward = upward;
    }

    public boolean isAscendent() {
        return upward;
    }

    public void setAscendent(boolean ascendent) {
        this.upward = ascendent;
    }
}
