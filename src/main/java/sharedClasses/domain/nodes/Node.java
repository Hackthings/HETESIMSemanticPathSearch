package main.java.sharedClasses.domain.nodes;

public class Node {
    private int id;
    private String name;
    private static int maxId;

    public Node(String name, int id) {
        this.name = name;
        this.id = id;
        if (maxId < id) maxId = id;
    }

    public static int getMaxId() {
        return maxId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
