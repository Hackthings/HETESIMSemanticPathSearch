package sharedClasses.utils;

import java.util.Comparator;

public class Vertex implements Comparable<Vertex> {
    private int first;      //Primer element del parell: fila
    private int second;     //Segon element del parell: columna
    private double value;   //Tercer element del parell: valor

    //Constructora de la classe: crea un nou objecte.
    //Pre: Cert.
    //Post:Retorna un nou objecte del tipus pair.
    public Vertex(int first, int second, double value){
        this.first = first;
        this.second = second;
        this.value = value;
    }

    //Pre: Cert.
    //Post: Retorna el valor de la fila del paràmetre implícit.
    public int getFirst(){
        return this.first;
    }

    //Pre: Cert.
    //Post: Retorna el valor de la columna paràmetre implícit.
    public int getSecond(){
        return this.second;
    }

    //Pre: Cert.
    //Post: Retorna el valor del paràmetre implícit.
    public double getValue() { return this.value; }

    //Pre: Cert.
    //Post: Canvia el valor de la clau del paràmetre implícit per first.
    public void setFirst(int first){
        this.first = first;
    }

    //Pre: Cert.
    //Post: Canvia el valor del valor del paràmetre implícit per valor.
    public void setSecond(int second){
        this.second = second;
    }

    //Pre: Cert.
    //Post: Canvia el valor del valor del paràmetre implícit per valor.
    public void setValue(double value){
        this.value = value;
    }

    //Pre: Cert
    //Post: Compara el paràmetre implícit amb el paràmetre passat.
    @Override
    public int compareTo(Vertex p) {
        if (getFirst() > p.getFirst()) {
            return 1;
        } else if (getFirst() == p.getFirst()) {
            if (getSecond() > p.getSecond()) {
                return 1;
            } else if (getFirst() == p.getSecond()) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public static class Comparators {
        public static final Comparator<Vertex> Comparator = (Vertex v1, Vertex v2) -> Double.compare(v1.getValue(), v2.getValue());
    }
}
