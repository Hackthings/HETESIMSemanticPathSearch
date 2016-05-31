package main.java.sharedClasses.domain.nodes;

import java.io.Serializable;

public class Node implements Serializable{
    private int id;
    private String name;

    /**
     * Constructor function for the Node class.
     * <p>
     * The Node class represents a node in our graph.
     *
     * @param  name the name of the conference
     * @param  id the id of the conference
     * @see         Node
     */
    public Node(String name, int id) {
        this.name = name;
        this.id = id;
    }


    /**
     * Returns the name of the node
     *
     * @return      the name of the node
     * @see         String
     */
    public String getName() {
        return name;
    }



    /**
     * Sets the name of the node
     *
     * @param name the name of the node to be set
     * @see         String
     */
    public void setName(String name) {
        this.name = name;
    }



    /**
     * Returns the id of the node
     *
     * @return      the id of the node
     * @see         String
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id of the node
     *
     * @param id the id of the node to be set
     * @see         String
     */
    public void setId(int id) {
        this.id = id;
    }
}
