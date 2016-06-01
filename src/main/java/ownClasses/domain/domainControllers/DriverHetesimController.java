package main.java.ownClasses.domain.domainControllers;


import main.java.sharedClasses.utils.Matrix;
import main.java.sharedClasses.utils.Vertex;

import java.util.ArrayList;

public class DriverHetesimController {

    private static void escriu_matriu(Matrix m) {
        for (int i : m.rows()) {
            for (int j : m.cols()) {
                System.out.println(" Fila " + i + ", columna " + j + ", valor: " + m.getValue(i, j));
            }
        }
    }

    public static void main(String[] args) {
        Matrix ap = new Matrix();

        Matrix tp = new Matrix();

        Matrix cp = new Matrix();

        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 2; ++y) {
                double valor = y % 2;
                ap.addValue(x, y, valor);
            }
        }
        for (int x = 3; x < 6; ++x) {
            for (int y = 0; y < 2; ++y) {
                double valor = y % 2;
                tp.addValue(x, y, valor);
            }
        }
        for (int x = 6; x < 7; ++x) {
            for (int y = 0; y < 2; ++y) {
                double valor = y % 2;
                cp.addValue(x, y, valor);
            }
        }

        DomainHetesimController h = new DomainHetesimController(ap,ap.transpose(),tp,tp.transpose(),cp,cp.transpose());

        Matrix resultat = h.heteSim("APAPAPCPA");

        escriu_matriu(resultat);

    }
}
