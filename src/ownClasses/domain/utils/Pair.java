package ownClasses.domain.utils;

public class Pair<Integer,Double> {
    private int first;    //Primer element del parell: clau
    private double second;   //Segon element del parell: valor

    //Constructora de la classe: crea un nou objecte.
    //Pre: Cert.
    //Post:Retorna un nou objecte del tipus pair.
    public Pair(int first, double second){
        this.first=first;
        this.second=second;
    }

    //Pre: Cert.
    //Post: Retorna la clau del paràmetre implícit.
    public int getFirst(){
        return first;
    }

    //Pre: Cert.
    //Post: Retorna el valor del paràmetre implícit.
    public double getSecond(){
        return second;
    }

    //Pre: Cert.
    //Post: Canvia el valor de la clau del paràmetre implícit per first.
    public void setFirst(int first){
        this.first=first;
    }

    //Pre: Cert.
    //Post: Canvia el valor del valor del paràmetre implícit per valor.
    public void setSecond(double second){
        this.second=second;
    }
}
