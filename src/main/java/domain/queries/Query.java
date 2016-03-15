package main.java.domain.queries;

public class Query {
    private String path;

    public Query(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
