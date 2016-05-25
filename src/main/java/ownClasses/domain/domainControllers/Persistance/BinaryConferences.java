package main.java.ownClasses.domain.domainControllers.Persistance;

import main.java.sharedClasses.domain.nodes.Conference;

import java.util.HashMap;

/**
 * Created by iansangines on 25/05/2016.
 */
public class BinaryConferences extends BinaryPersistance<HashMap<Integer,Conference>> {

    public BinaryConferences(){
        super();
    }

    public boolean write(HashMap<Integer, Conference> element) {
        return super.write(element, "conferences.dat");
    }

    public HashMap<Integer, Conference> read() {
        return super.read("conferences.dat");
    }
}
