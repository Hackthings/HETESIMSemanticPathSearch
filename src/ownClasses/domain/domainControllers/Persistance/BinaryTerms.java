package ownClasses.domain.domainControllers.Persistance;

import sharedClasses.domain.nodes.Term;

import java.util.HashMap;

/**
 * Created by iansangines on 25/05/2016.
 */
public class BinaryTerms extends BinaryPersistance<HashMap<Integer,Term>> {

    public BinaryTerms(){
        super();
    }

    public boolean write(HashMap<Integer, Term> element) {
        return super.write(element, "/terms.dat");
    }

    public HashMap<Integer, Term> read() {
        return super.read("/terms.dat");
    }
}
