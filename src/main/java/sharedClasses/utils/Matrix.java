package main.java.sharedClasses.utils;


import java.util.Set;
import java.util.LinkedList;
import java.util.HashMap;

public class Matrix {
    private HashMap<Integer, LinkedList<Pair<Integer, Double>>> matrix; //aquest es el format de la matriu que va dir el borja, podem discutir altres si us es mes facil o ho creieu millor. En la nostra practica Double sera segurament Double
    private HashMap<Integer, LinkedList<Pair<Integer, Double>>> mTrans;

    //Constructora de la classe
    //Pre: Cert
    //Post: Crea una matriu buida i inicialitza el pair
    public Matrix() {
        this.matrix = new HashMap<Integer, LinkedList<Pair<Integer, Double>>>();
        this.mTrans = new HashMap<Integer, LinkedList<Pair<Integer, Double>>>();
    }

    public Matrix(HashMap<Integer, LinkedList<Pair<Integer, Double>>> m, HashMap<Integer, LinkedList<Pair<Integer, Double>>> mTrans) {
        this.matrix = m;
        this.mTrans = mTrans;
    }

    //Pre: Cert
    //Post: Retorna un vector amb les files valides
    public Set<Integer> rows() {
        return this.matrix.keySet();
    }

    //Pre: Cert
    //Post: Retorna un vector amb les columnes valides
    public Set<Integer> cols() {
        return this.mTrans.keySet();
    }


    //Pre: Existeix la fila key
    //Post: Retorna un vector amb les columnes valides de la fila key
    public LinkedList<Pair<Integer, Double>> columns(int key) {
        return this.matrix.get(key);
    }


    //Pre: Cert
    //Post: Afegeix una fila buida (un LinkedList<Pair<Integer, Double>> buit)
    public void addRow(int i){
        this.matrix.put(i, new LinkedList<Pair<Integer, Double>>());
    }


    //Pre: Cert
    //Post: Elimina la fila i de la matriu (elimina l'ArrayList amb id i)
    public void deleteRow(int i){
        LinkedList<Pair<Integer, Double>> tmp = this.matrix.remove(i);
    }


    //Pre: Cert
    //Post: Afegeix el valor value a la posició i,j. Afegeix una fila si es necessari.
    //      Si el valor es 0, comprova si cal eliminar la fila.
    public void addValue(int i, int j, double value){
        if (this.matrix.containsKey(i)) {
            if (value == 0) {
                this.matrix.get(i).remove(j);
                this.mTrans.get(j).remove(i);
            }
            else {
                this.matrix.get(i).add(j, new Pair<Integer, Double>(j, value));
                this.mTrans.get(j).add(i, new Pair<Integer, Double>(i, value));
            }
        } else if (value != 0) {
            addRow(i);
            this.matrix.get(i).add(j, new Pair<Integer, Double>(j, value));
            this.mTrans.get(j).add(i, new Pair<Integer, Double>(i, value));
        }
    }


    //Pre: Cert.
    //Post: Retorna el valor de i, j, si existeix, sino retorna -1.
    public double getValue(int i, int j){
        if (this.matrix.containsKey(i)) {
            LinkedList<Pair<Integer, Double>> list = this.matrix.get(i);
            if (list.contains(j)) {
                Pair<Integer, Double> p = list.get(i);
                return p.getSecond();

            } else return -1;
        } else return -1;
    }


    //Pre: Cert
    //Post: Retorna la matriu transposada.
    public Matrix transpose(){
        Matrix m = new Matrix(this.mTrans, this.matrix);
        return m;
    }


    //Pre: el paràmetre implícit té el mateix nombre de columnes que files té m
    //Post: retorna la matriu producte del paràmetre implícit amb m
    public Matrix multiply(Matrix m){

        try {
            if (rows().size() == m.rows().size()) {
                throw new Exception("No es poden multiplicar les matrius!");
            }
        } catch (Exception error) {

        }
        //TODO comprovar el Pre i llançar excepcio
        Matrix mult = new Matrix();
        Set<Integer> files = rows();
        Set<Integer> columnes = m.cols();

        for (int x : files) {
            for (int y : columnes) {
                double valor = 0;
                for (int z = 0; z < columns(x).size(); z++) {
                    double val1 = getValue(x, z);
                    double val2 = m.getValue(z, y);
                    if (val1 != -1 && val2 != -1) {
                        valor += val1 * val2;
                    }
                }
                mult.addValue(x, y, valor);
            }
        }

        /*for (int x = 0; x < rows().size(); ++x) {
            for (int y = 0; y < m.colums(x).size(); y++) {
                double valor = 0;
                for (int z = 0; z < colums(x).size(); z++) {
                    double val1 = getValue(x, z);
                    double val2 = m.getValue(z, y);
                    if (val1 == -1) val1 = 0;
                    if (val2 == -1) val2 = 0;
                    valor += val1 * val2;
                }
                mult.addValue(x, y, valor);
            }
        }*/
        return mult;
    }

    //Pre: Cert
    //Post: retorna el modul de la fila i. Si no existeix, retorna 0.
    public double rowModulus(int i) {
        if (this.matrix.containsKey(i)) {
            LinkedList<Pair<Integer, Double>> list = this.matrix.get(i);
            double m = 0;
            for (Pair par : list) {
                m += par.getSecond() * par.getSecond();
            }
            return Math.sqrt(m);
        } else return 0;
    }


    //Pre: Cert
    //Post: retorna el modul de la columna j. Si no existeix, retorna 0.
    public double columModulus(int j) {
        if (this.mTrans.containsKey(j)) {
            LinkedList<Pair<Integer, Double>> list = this.mTrans.get(j);
            double m = 0;
            for (Pair par : list) {
                m += par.getSecond() * par.getSecond();
            }
            return Math.sqrt(m);
        } else return 0;
    }


    //Pre: La matriu conte nomes 1s i 0s (no cal tirar excepcio)
    //Post: Divideix totes les files per el nombre de valors
    public Matrix normalize(){
        Matrix n = new Matrix();
        for (int i : this.matrix.keySet()) {
            double valor = rows().size();
            for (Pair<Integer, Double> p : this.matrix.get(i)) {
                n.addValue(i, p.getFirst(), p.getSecond()/valor);
            }
        }
        return n;
    }

}

