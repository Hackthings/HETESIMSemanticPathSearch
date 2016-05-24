package main.java.sharedClasses.utils;


import sun.reflect.generics.tree.Tree;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.TreeMap;


public class Matrix {
    private HashMap<Integer, HashMap<Integer, Double>> matrix;
    private HashMap<Integer, HashMap<Integer, Double>> mTrans;

    //Constructora de la classe
    //Pre: Cert
    //Post: Crea una matriu buida i inicialitza el pair
    public Matrix() {
        this.matrix = new HashMap<Integer, HashMap<Integer, Double>>();
        this.mTrans = new HashMap<Integer, HashMap<Integer, Double>>();
    }

    private Matrix(HashMap<Integer, HashMap<Integer, Double>> m, HashMap<Integer, HashMap<Integer, Double>> mTrans) {
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
    public HashMap<Integer, Double> columns(int key) {
        return this.matrix.get(key);
    }


    //Pre: Cert
    //Post: Afegeix una fila buida (un HashMap<Integer, Double>> buit)
    private void addRow(int i){
        this.matrix.put(i, new HashMap<Integer, Double>());
    }

    private void addRowTranspose(int j) {
        this.mTrans.put(j, new HashMap<Integer, Double>());
    }


    //Pre: Cert
    //Post: Elimina la fila i de la matriu (elimina l'ArrayList amb id i)
    public void deleteRow(int i){
        HashMap<Integer, Double> tmp = this.matrix.remove(i);
        HashMap<Integer, Double> tmp2 = this.mTrans.remove(i);
    }

    //Pre: Existeix la posicio i,j
    //Post: Elimina el valor de la posicio i,j
    private void deleteValue(int i, int j) {
        this.matrix.get(i).remove(j);
        this.mTrans.get(j).remove(i);
    }


    //Pre: Cert
    //Post: Afegeix el valor value a la posició i,j. Afegeix una fila si es necessari.
    //      Si el valor es 0, comprova si cal eliminar la fila.
    public void addValue(int i, int j, double value){
        if (value == 0) {
            if (getValue(i, j) != -1) {
                deleteValue(i, j);
            }
        } else {
            if (getValue(i, j) != -1) {
                this.matrix.get(i).replace(j, value);
                this.mTrans.get(j).replace(i, value);
            } else {
                if (!this.matrix.containsKey(i)) {
                    addRow(i);
                }
                if(!this.mTrans.containsKey(j)){
                    addRowTranspose(j);
                }
                this.matrix.get(i).put(j, value);
                this.mTrans.get(j).put(i, value);
            }
        }
    }


    //Pre: Cert.
    //Post: Retorna el valor de i, j, si existeix, sino retorna -1.
    public double getValue(int i, int j){
        if (this.matrix.containsKey(i)) {
            if (this.matrix.get(i).containsKey(j)) {
                return this.matrix.get(i).get(j);

            } else return -1;
        } else return -1;
    }


    //Pre: Cert
    //Post: Retorna la matriu transposada.
    public Matrix transpose(){
        return new Matrix(this.mTrans, this.matrix);
    }


    //Pre: el paràmetre implícit té el mateix nombre de columnes que files té m
    //Post: retorna la matriu producte del paràmetre implícit amb m
    public Matrix multiply(Matrix m){
        /*try {
            //if (cols().size() != m.rows().size()) {
                //throw new Exception("No es poden multiplicar les matrius!");
            //}
        } catch (Exception error) {
            //Falta error
        }*/
        Matrix mult = new Matrix();
        Set<Integer> r = rows();
        Set<Integer> c = m.cols();

        System.out.println("inici multiplicacio");
        int tam = r.size();
        int i = 0;
        int j = 0;
        System.out.println(j+"%");
        for (int x : r) {
            int ji = (i*100)/tam;
            if(j<ji){
                System.out.println(ji+"%");
                j = ji;
            }

            for (int y : c) {
                double value = 0;
                Set<Integer> cr = columns(x).keySet();
                for (int z : cr) {
                    double val1 = getValue(x, z);
                    double val2 = m.getValue(z, y);
                    if (val1 != -1 && val2 != -1) {
                        value += val1 * val2;
                    }
                }
                mult.addValue(x, y, value);
            }
            ++i;
        }

        return mult;
    }

    //Pre: Cert
    //Post: retorna el modul de la fila i. Si no existeix, retorna 0.
    public double rowModulus(int i) {
        if (this.matrix.containsKey(i)) {
            double m = 0;
            for (int j : columns(i).keySet()) {
            	double d = getValue(i, j);
                m += d*d;
            }
            return Math.sqrt(m);
        } else return 0;
    }


    //Pre: Cert
    //Post: retorna el modul de la columna j. Si no existeix, retorna 0.
    public double columModulus(int j) {
        if (this.mTrans.containsKey(j)) {
            Set<Integer> list = this.mTrans.get(j).keySet();
            double m = 0;
            for (int i : list) {
                m += this.mTrans.get(j).get(i) * this.mTrans.get(j).get(i);
            }
            return Math.sqrt(m);
        } else return 0;
    }


    //Pre: La matriu conte nomes 1s i 0s (no cal tirar excepcio)
    //Post: Divideix totes les files per el nombre de valors
    public Matrix normalize(){
        Matrix n = new Matrix();
        for (int i : this.matrix.keySet()) {
            double valor = columns(i).keySet().size();
            for (int j : this.matrix.get(i).keySet()) {
                n.addValue(i, j, getValue(i,j)/valor);
            }
        }
        return n;
    }
    
    public HashMap<Integer, Double> rows(int key) {
        return this.mTrans.get(key);
    }
    
    public Matrix normalizeRows(){
    	Matrix n = new Matrix();
        for (int j : this.mTrans.keySet()) {
            double valor = rows(j).keySet().size();
            for (int i : this.mTrans.get(j).keySet()) {
                n.addValue(i, j, getValue(i,j)/valor);
            }
        }
        return n;
    }
    
    public void print(){
    	System.out.println("-------");
    	for(Integer x : rows()){
    		for(Integer y : columns(x).keySet()){
    			System.out.print(x);
    			System.out.print(",");
    			System.out.print(y);
    			System.out.print(":");
    			System.out.println(getValue(x,y));
    		}
    	}
    	System.out.println("-------");
    }

}
