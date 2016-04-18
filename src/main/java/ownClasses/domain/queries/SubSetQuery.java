package main.java.ownClasses.domain.queries;

public class SubSetQuery extends Query {
    private String restriction;
    private String nodeType;

    public SubSetQuery(String path, String restriction, String nodeType) {
        super(path);
        this.restriction = restriction;
        this.nodeType = nodeType;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}
