package main.java.data.nodes;

public class Node {
    int id;
    String name;
    static int maxId;

    public Node(String name) {
        ++maxId;
        id = maxId;
        this.name = name;
    }

    public int getId() {
        return id;
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
}
