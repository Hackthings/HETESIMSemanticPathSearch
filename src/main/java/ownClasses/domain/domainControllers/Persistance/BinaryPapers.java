package main.java.ownClasses.domain.domainControllers.Persistance;

import main.java.sharedClasses.domain.nodes.Paper;
import java.util.HashMap;

/**
 * Created by iansangines on 25/05/2016.
 */
public class BinaryPapers extends BinaryPersistance<HashMap<Integer,Paper>> {

    public BinaryPapers(){
        super();
    }


    public boolean write(HashMap<Integer, Paper> element) {
        return super.write(element, "papers.dat");
    }

    public HashMap<Integer, Paper> read() {
        return super.read("papers.dat");
    }
}
