package main.java.ownClasses.domain.domainControllers.Drivers;



import main.java.sharedClasses.utils.Matrix;

import java.util.Set;

public class Matrix_driver {

    private static void escriu_matriu(Matrix m) {
        for (int i : m.rows()) {
            for (int j : m.cols()) {
                System.out.println(" Fila " + i + ", columna " + j + ", valor: " + m.getValue(i, j));
            }
        }
    }

    public static void main(String[] args) {

        //Crea un objecte de la classe matrix:
        Matrix m = new Matrix();
        System.out.println("Matriu buida creada");

        //Afegim valors per crear una matriu 3x2
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 2; ++y) {
                double valor = y % 2;
                m.addValue(x, y, valor);
                System.out.println("Afegit el valor " + valor + " a la posicio" + x + "," + y);
            }
        }


        //Esborra la fila 2 de la matriu.

        System.out.println("S'ha esborrat la fila 2");
        escriu_matriu(m);

        //Imprimeix les files vàlides (0 i 1) i el seu mòdul.
        Set<Integer> files = m.rows();
        System.out.println("Files vàlides: ");
        for (int x : files) {
            System.out.println("Fila: " + x);
            System.out.println("Mòdul de la fila: " + m.rowModulus(x));
        }

        //Imprimeix les columnes vàlides (0 i 1) i el seu mòdul.
        Set<Integer> columnes = m.cols();
        System.out.printf("Columnes vàlides: ");
        for (int x : columnes) {
            System.out.println("Columna: " + x);
            System.out.println("Mòdul de la columna: " + m.columModulus(x));
        }

        //Multiplica m x m.
        Matrix mult = m.multiply(m);
        System.out.println("Matriu multiplicació: ");
        escriu_matriu(mult);

        //Normalitza la matriu mult.
        Matrix norm = mult.normalize();
        System.out.println("Matriu multiplicació normalitzada: ");
        escriu_matriu(norm);

        //Transposa la matriu m.
        Matrix trans = m.transpose();
        System.out.println("Matriu transposada: ");
        escriu_matriu(trans);


    }

}
