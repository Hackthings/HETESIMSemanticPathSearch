package ownClasses.domain.domainControllers.Drivers;


import ownClasses.domain.utils.Pair;

public class Pair_driver {

    public static void main(String[] args) {

        //Crea un objecte de la classe amb clau 1 i valor 2.5
        Pair p = new Pair(1, 2.5);
        System.out.println("S'ha creat un pair amb clau = 1 i valor = 2.5");

        //Consulta la clau de p: 1
        System.out.println("La clau ha de ser 1");
        System.out.println("Clau: " + p.getFirst());

        //Consulta el valor de p: 2.5
        System.out.println("El valor ha de ser 2.5");
        System.out.println("Valor: " + p.getSecond());

        //Modifica la clau de p: 2
        p.setFirst(2);
        System.out.println("La clau ha de ser 2");
        System.out.println("Clau modificada: " + p.getFirst());

        //Modifica el valor de p: 4.12
        p.setSecond(4.12);
        System.out.println("El  valor ha de ser 4.12");
        System.out.println("Valor modificat: " + p.getSecond());
    }

}
