package main.java.ownClasses.domain.domainControllers;


import main.java.sharedClasses.utils.Matrix;
import main.java.sharedClasses.utils.Vertex;

import java.util.ArrayList;

public class DomainHetesimController {
    private Matrix probAuthorPaper;
    private Matrix probPaperAuthor;
    private Matrix probTermPaper;
    private Matrix probPaperTerm;
    private Matrix probConferencePaper;
    private Matrix probPaperConference;

    private Matrix auxiliarObjectL;
    private Matrix auxiliarObjectR;

    DomainHetesimController(Matrix authorPaper, Matrix paperAuthor, Matrix termPaper, Matrix paperTerm, Matrix conferencePaper, Matrix paperConference) {
        probAuthorPaper = authorPaper.normalize();
        probPaperAuthor = paperAuthor.normalize();
        probTermPaper = termPaper.normalize();
        probPaperTerm = paperTerm.normalize();
        probConferencePaper = conferencePaper.normalize();
        probPaperConference = paperConference.normalize();

        auxiliarObjectL = new Matrix();
        auxiliarObjectR = new Matrix();
    }

    private Matrix findMatrix(char source, char target) {
        if (source == 'A') return probAuthorPaper;
        if (source == 'T') return probTermPaper;
        if (source == 'C') return probConferencePaper;
        if (target == 'A') return probPaperAuthor;
        if (target == 'T') return probPaperTerm;
        return probPaperConference;
    }

    private Matrix findMatrix(char source, char target, char side) {
        if (side == 'l' && target == 'E') return auxiliarObjectL;
        if (side == 'r' && target == 'E') return auxiliarObjectR;
        return findMatrix(source, target);
    }

    Matrix heteSim(String path) {
        String pl, pr;
        //create auxiliary matrices (if needed) and split pl/pr
        if (path.length() % 2 == 0) {
            int mid = path.length() / 2;
            char in = path.charAt(mid - 1);
            char out = path.charAt(mid);
            generateE(in, out);
            pl = path.substring(0, mid);
            pl = pl.concat("E");
            pr = "E";
            pr = pr.concat(path.substring(mid));
        } else {
            int mid = path.length() / 2;
            pl = path.substring(0, mid + 1);
            pr = path.substring(mid);
        }
        String pri = new StringBuilder(pr).reverse().toString(); //inverted path
        boolean symmetric = (pl.equals(pri));

        final boolean[] end1 = {false};
        final boolean[] end2 = {false};

        //PMpl
        final Matrix[] pmPl = {findMatrix(pl.charAt(0), pl.charAt(1), 'l')};
        String finalPl = pl;
        new Thread(() ->{
            for (int i = 2; i < finalPl.length(); ++i)
                pmPl[0] = pmPl[0].multiply(findMatrix(finalPl.charAt(i - 1), finalPl.charAt(i), 'l'));
            end1[0] = true;
        }).start();
        //PMpri
        final Matrix[] pmPri = {new Matrix()};

        if(symmetric){
            pmPri[0] = pmPl[0];
            end2[0] = true;
        }
        else {
            final ArrayList<Matrix> matrices = new ArrayList<Matrix>();
            matrices.add(findMatrix(pri.charAt(0), pri.charAt(1), 'r'));
            for (int i = 2; i < pri.length(); ++i)
                matrices.add(findMatrix(pri.charAt(i - 1), pri.charAt(i), 'r'));
            new Thread(() -> {
                Matrix m = matrices.get(0);
                for (int i = 1; i < matrices.size(); ++i)
                    m = m.multiply(matrices.get(i));
                pmPri[0] = m;
                end1[0] = true;
            }).start();
        }

        //wait for secondary thread
        while(!end1[0] || !end2[0]){
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                System.out.println("Interrupred Exception");
                return new Matrix();
            }
        }

        Matrix result = pmPl[0].multiply(pmPri[0].transpose());

        Double rmod = 0.0;
        Double cmod = 0.0;
        Matrix r2 = result;
        result = new Matrix();
        for (Integer row : r2.rows()) {
            rmod = pmPl[0].rowModulus(row);
            for (Vertex column : r2.getRow(row)) {
                cmod = pmPri[0].rowModulus(column.getSecond());
                result.addValue(row, column.getSecond(), r2.getValue(row, column.getSecond()) / (rmod * cmod));
            }
        }
        return result;
    }

    private void generateE(char source, char target) {
        Matrix middleMatrix = findMatrix(source, target);
        auxiliarObjectL = new Matrix();
        auxiliarObjectR = new Matrix();
        //add links
        int c = 0;
        int i = 0;
        for (int row : middleMatrix.rows()) {
            for (Vertex column : middleMatrix.getRow(row)) {
                auxiliarObjectL.addValue(row, i, middleMatrix.getValue(row, column.getSecond()));
                auxiliarObjectR.addValue(i, column.getSecond(), 1.0);
                i += 1;
            }
        }
        auxiliarObjectR = auxiliarObjectR.transpose().normalize();
    }
}