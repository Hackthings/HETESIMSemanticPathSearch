package main.java.sharedClasses.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Matrix {
    private Pair<Integer, Double> p; //Objecte de la classe Pair
    private HashMap<Integer, ArrayList<Pair<Integer, Double>>> matrix; //aquest es el format de la matriu que va dir el borja, podem discutir altres si us es mes facil o ho creieu millor. En la nostra practica Double sera segurament Double

    //Constructora de la classe
    //Pre: Cert
    //Post: Crea una matriu buida i inicialitza el pair
    public Matrix() {
        this.matrix = new HashMap<Integer, ArrayList<Pair<Integer, Double>>>();
        p = new Pair<Integer, Double>(0,0.0);
    }

    //Pre: Cert
    //TODO Post: Retorna un vector amb les files valides
    public int rows() {
        return this.matrix.size();
    }

    //Pre: Cert
    //TODO Post: Retorna un vector amb les columnes valides de la fila key
    public int colums(int key) {
        if (this.matrix.isEmpty()) return 0;
        else {
            ArrayList<Pair<Integer,Double>> tmp = this.matrix.get(key);
            return tmp.size();
        }
    }

    //Pre: Cert
    //Post: Afegeix una fila buida (un ArrayList<Pair<Integer, Double>> buit)
    public void addRow(int i){
        this.matrix.put(i, new ArrayList<Pair<Integer, Double>>());
    }

    //Pre: Cert
    //Post: Elimina la fila i de la matriu (elimina l'ArrayList amb id i)
    public void deleteRow(int i){
        ArrayList<Pair<Integer,Double>> tmp = this.matrix.remove(i);
    }

    //Pre: Cert
    //Post: Afegeix el valor value a la posició i,j. Afegeix una fila si es necessari.
    public void addValue(int i, int j, double value){
        //TODO si value es 0 comprovar si cal eliminar la fila
        if (this.matrix.containsKey(i)) {
            p = new Pair<Integer,Double>(j, value);
            this.matrix.get(i).add(j, p);
        } else {
            addRow(i);
            this.matrix.get(i).add(j, new Pair<Integer,Double>(j, value));
        }

    }

    //Pre: Cert.
    //Post: Retorna el valor de i, j, si existeix, sino retorna -1.
    public double getValue(int i, int j){
        if (this.matrix.containsKey(i)) {
            ArrayList<Pair<Integer, Double>> list = this.matrix.get(i);
            if (list.contains(j)) {
                p = list.get(i);
                return p.getSecond();

            } else return -1;
        } else return -1;
    }

    //TODO divideix totes les files per el nombre de valors
    //Pre: la matriu conte nomes 1s i 0s (no cal tirar excepcio)
    public Matrix normalize(){

    }

    //Pre: Cert
    //Post: Retorna la matriu transposada.
    public Matrix transpose(){
        Matrix m = new Matrix();
        Iterator<Integer> it = this.matrix.keySet().iterator();
        int fila;
        while (it.hasNext()) {
            fila = it.next();
            ArrayList<Pair<Integer, Double>> list = this.matrix.get(fila);
            for (Pair col : list) {
                m.addValue(col.getFirst(), fila, col.getSecond());
            }
        }
        return m;
    }


    //Pre: el paràmetre implícit té el mateix nombre de columnes que files té m
    //Post: retorna la matriu producte del paràmetre implícit amb m
    public Matrix multiply(Matrix m){
        //TODO comprovar el Pre i llançar excepcio
        Matrix mult = new Matrix();
        for (int x = 0; x < rows(); ++x) {
            for (int y = 0; y < m.colums(x); y++) {
                double valor = 0;
                for (int z = 0; z < colums(x); z++) {
                    double val1 = getValue(x, z);
                    double val2 = m.getValue(z, y);
                    if (val1 == -1) val1 = 0;
                    if (val2 == -1) val2 = 0;
                    valor += val1*val2;
                }
                mult.addValue(x, y, valor);
            }
        }
        return mult;
    }

    //Pre: Cert
    //Post: retorna el modul de la fila i.
    public double modulus(int i) {
        //si la fila no existeix retorna 0
        ArrayList<Pair<Integer, Double>> list = this.matrix.get(i);
        double m = 0;
        for (Pair par : list) {
            m += par.getSecond()*par.getSecond();
        }
        return Math.sqrt(m);
    }
}
