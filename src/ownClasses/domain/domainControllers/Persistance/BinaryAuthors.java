package main.java.ownClasses.domain.domainControllers.Persistance;

import main.java.sharedClasses.domain.nodes.Author;

import java.util.HashMap;

/**
 * Created by iansangines on 25/05/2016.
 */
public class BinaryAuthors extends BinaryPersistance<HashMap<Integer,Author>>{

    public BinaryAuthors(){
        super();
    }

    public boolean write(HashMap<Integer, Author> element) {
        return super.write(element, "/authors.dat");
    }

    public HashMap<Integer, Author> read() {
        return super.read("/authors.dat");
    }
}
