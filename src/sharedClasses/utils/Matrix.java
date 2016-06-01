package sharedClasses.utils;

import java.util.*;

public class Matrix {
    private HashMap<Integer, LinkedList<Vertex>> matrix;
    private HashMap<Integer, LinkedList<Vertex>> mTrans;

    /**
     * Basic constructor function for the Matrix.
     *
     * @see         Matrix
     */
    public Matrix() {
        this.matrix = new LinkedHashMap<>();
        this.mTrans = new LinkedHashMap<>();
    }

    /**
     * Constructor function for the Matrix class.
     *
     * @param  m the matrix HashMap
     * @param  mTrans the transposed matrix HashMap
     * @see         Matrix
     */
    private Matrix(HashMap<Integer, LinkedList<Vertex>> m, HashMap<Integer, LinkedList<Vertex>> mTrans) {
        this.matrix = m;
        this.mTrans = mTrans;
    }

    /**
     * Returns the valid rows of the Matrix
     *
     * @return      the valid rows of the Matrix
     * @see         Set
     */
    public Set<Integer> rows() {
        return this.matrix.keySet();
    }

    /**
     * Returns the valid columns of the Matrix
     *
     * @return      the valid columns of the Matrix
     * @see         Set
     */
    public Set<Integer> cols() {
        return this.mTrans.keySet();
    }

    /**
     * Returns the rows related to the column key of the Matrix
     *
     * @param   key the column of the Matrix which has to be the key for the rows we want
     * @return      the rows related to the column key of the Matrix
     * @see         LinkedList
     */
    public LinkedList<Vertex> getRow(int key) {
        return this.matrix.get(key);
    }

    /**
     * Returns the columns related to the row key of the Matrix
     *
     * @param   key the row of the Matrix which has to be the key for the columns we want
     * @return      the columns related to the column key of the Matrix
     * @see         LinkedList
     */
    private LinkedList<Vertex> getColumn(int key) {
        return this.mTrans.get(key);
    }

    /**
     * Adds a row to the Matrix
     *
     * @param  i the position where we want to put the row
     * @param  l the row we want to add to the Matrix
     */
    private void addRow(int i, LinkedList<Vertex> l){
        this.matrix.put(i, l);
    }

    /**
     * Adds a transposed row to the Matrix
     *
     * @param  j the position where we want to put the row
     * @param  l the row we want to add to the Matrix
     */
    private void addRowTranspose(int j, LinkedList<Vertex> l) {
        this.mTrans.put(j, l);
    }

    /**
     * Deletes the value at the (i, j) position of the Matrix
     *
     * @param  i the row where the value is located
     * @param  j the coloumn where the value is located
     */
    private void deleteValue(int i, int j) {
        try {
            this.matrix.get(i).remove(j);
            this.mTrans.get(j).remove(i);
        } catch (NullPointerException e) {
            System.out.println("No existeix la posició " + i + "," + j);
        }
    }


    /**
     * Adds the value at the (i, j) position of the Matrix
     *
     * @param  i the row where the value is located
     * @param  j the coloumn where the value is located
     * @param  value the value to add at (i, j)
     */
    public void addValue(int i, int j, double value){
        if (value == 0) { //Si el valor es 0 i existeix a la matriu, s'elimina.
            if (getValue(i, j) != -1) {
                deleteValue(i, j);
            }
        } else {    //Sino:
            Vertex p = new Vertex(i, j, value);
            Vertex pt = new Vertex(j, i, value);
            //Insercio a matrix:
            if (rows().contains(i)) {
                if (getRow(i).getLast().getSecond() < j) { //Si l'index de la columna és més gran que la columna, inserta al final.
                    getRow(i).addLast(p);
                } else {        //Sinó busca on ha d'insertar.
                    ListIterator<Vertex> it = getRow(i).listIterator();
                    Vertex aux = null;
                    if (it.hasNext())aux = it.next();
                    //Busca la posició on l'ha d'insertar.
                    while (aux.getSecond() < j) {
                        if (it.hasNext()) aux = it.next();
                    }
                    aux = it.previous();
                    it.add(p);
                }
            } else { //Sino crea una nova columna amb el valor.
                LinkedList<Vertex> newRow = new LinkedList<>();
                newRow.add(p);
                addRow(i, newRow);
            }

            //Insercio a mTrans:
            if (cols().contains(j)) {
                if (getColumn(j).getLast().getSecond() < i) {//Si l'index de la columna és més gran que la columna, inserta al final.
                    getColumn(j).addLast(pt);
                } else {    //Sinó busca on ha d'insertar.
                    ListIterator<Vertex> it = getColumn(j).listIterator();
                    Vertex aux = null;
                    if (it.hasNext()) aux = it.next();
                    //Busca la posició on l'ha d'insertar.
                    while (aux.getSecond() < i) {
                        if (it.hasNext()) aux = it.next();
                    }
                    aux = it.previous();
                    it.add(pt);
                }
            } else {    //Sino crea una nova columna amb el valor.
                LinkedList<Vertex> newCol = new LinkedList<>();
                newCol.add(pt);
                addRowTranspose(j, newCol);
            }
        }
        //Collections.sort(this.matrix.get(i));
        //Collections.sort(this.mTrans.get(j));
    }


    /**
     * Adds the value at the (i, j) position of the Matrix
     *
     * @param  i the row where the value is located
     * @param  j the coloumn where the value is located
     * @return  the value at the (i, j) position of the Matrix
     * @see double
     */
    public double getValue(int i, int j){
        if (this.matrix.containsKey(i)) {
            for (Vertex p : getRow(i)) {
                if (p.getSecond() == j) return p.getValue();
                else if (p.getSecond() > j) return -1;
            }
            return -1;
        } else return -1;
    }


    /**
     * Returns the transposed matrix of our Matrix
     *
     * @see         Matrix
     */
    public Matrix transpose(){
        return new Matrix(this.mTrans, this.matrix);
    }


    /**
     * Returns the product of our Matrix with the matrix m
     *
     * @param  m the matrix to be multiplied to our Matrix
     * @return   the product of our Matrix with the matrix m
     *
     * @see Matrix
     */
    public Matrix multiply(Matrix m){
        Matrix mult = new Matrix();
        Set<Integer> r = rows();
        Set<Integer> c = m.cols();

        int tam = r.size();
        int i = 0;
        int j = 0;

        for (int x : r) {

            int ji = (i*100)/tam;
            if(j<ji){
                System.out.println(ji+"%");
                j = ji;
            }

            for (int y : c) {
                double value = 0;
                ListIterator<Vertex> itR = getRow(x).listIterator();
                ListIterator<Vertex> itC = m.getColumn(y).listIterator();
                Vertex p, s;
                while (itR.hasNext() && itC.hasNext()) {
                    p = itR.next();
                    while (itC.hasNext()) {
                        s = itC.next();
                        if (p.getSecond() == s.getSecond()) {
                            value += p.getValue()*s.getValue();
                            break;
                        } else if (p.getSecond() < s.getSecond()) {
                            s = itC.previous();
                            break;
                        }
                    }
                }
                mult.addValue(x, y, value);
            }
            ++i;
        }
        return mult;
    }


    /**
     * Returns the division of every cell of our Matrix with every cell of the matrix m
     *
     * @param  m the matrix to do the divisions
     * @return   the division of every cell of our Matrix with every cell of the matrix m
     *
     * @see Matrix
     */
    public Matrix dividycells(Matrix m){
        Matrix div = new Matrix();
        Set<Integer> r = rows();
        for (int x : r) {
            ListIterator<Vertex> itA = getRow(x).listIterator();
            ListIterator<Vertex> itB = m.getRow(x).listIterator();
            while (itA.hasNext() && itB.hasNext()) {
                Vertex p = itA.next();
                Vertex s = itB.next();
                if (p.getSecond() == s.getSecond()) {
                    double val1 = p.getValue();
                    double val2 = s.getValue();
                    if (val1 != -1 && val2 != -1) {
                        double value = val1 / val2;
                        div.addValue(x, p.getSecond(), value);
                    }
                } else if (p.getSecond() > s.getSecond()) {
                    if (itB.hasNext()) itB.next();
                } else if (itA.hasNext()) itA.next();
            }
        }
        return div;
    }


    /**
     * Returns the modulous of the row i or 0 if the row doesn't exists
     *
     * @param  i the row we want to do the modulous
     * @return  the modulous of the row i or 0 if the row doesn't exists
     * @see double
     */
    public double rowModulus(int i) {
        if (this.matrix.containsKey(i)) {
            LinkedList<Vertex> list = this.matrix.get(i);
            double m = 0;
            for (Vertex p : list) {
                m += p.getValue() * p.getValue();
            }
            return Math.sqrt(m);
        } else return 0;
    }


    /**
     * Returns the modulous of the column i or 0 if the row doesn't exists
     *
     * @param  j the column we want to do the modulous
     * @return  the modulous of the column i or 0 if the row doesn't exists
     * @see double
     */
    public double columModulus(int j) {
        if (this.mTrans.containsKey(j)) {
            LinkedList<Vertex> list = this.mTrans.get(j);
            double m = 0;
            for (Vertex p : list) {
                m += p.getValue() * p.getValue();
            }
            return Math.sqrt(m);
        } else return 0;
    }

    /**
     * Normalizes the Matrix
     *
     * @return  the normalized Matrix
     * @see Matrix
     */
    public Matrix normalize(){
        Matrix n = new Matrix();
        Set<Integer> r = rows();
        for (int i : r) {
            double valor = 0;
            if (getRow(i) != null) {
                for (Vertex vertex : getRow(i)) {
                    double val = vertex.getValue();

                    valor += val * val;
                }

                valor = Math.sqrt(valor);

                for (ListIterator<Vertex> it = getRow(i).listIterator(); it.hasNext();) {
                    Vertex p = it.next();
                    n.addValue(i, p.getSecond(), p.getValue()/valor);
                }
            }

        }
        return n;
    }

}
