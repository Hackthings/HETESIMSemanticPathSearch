package main.java.ownClasses.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Matrix<K> {
    private HashMap<Integer, ArrayList<Pair<Integer, K>>> matrix; //aquest es el format de la matriu que va dir el borja, podem discutir altres si us es mes facil o ho creieu millor. En la nostra practica K sera segurament Double


    public Matrix() {} //crea matriu buida

    public void addRow(int i){} //afegeix un ArrayList<Pair<>> buit al Hashmap amb clau i

    public void deleteRow(int i){} //elimina una ArrayList del Hashmap

    public void addValue(int i, int j, K value){} //afegeix valor en la posicio i, j. Afegeix un row si es necessari(addRow)

    public K getValue(int i, int j){} //retorna el valor de i,j si existeix, sino retorna null o el que us vagi millor

    public Matrix transpose(){} //retorna la matriu transposada

    public Matrix multiply(Matrix m){} //retorna matrix*m

    public Double modulus(int i){} //retorna el modul de la fila i, no se si java us deixara fer operacions aritmetiques amb un template K. Si no us deixa simplement canvieu totes les K per Double

    //potser afegim mes funcions a mida que les anem necessitant
}
