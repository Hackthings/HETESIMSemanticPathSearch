package main.java.domainControllers;

import main.java.sharedClasses.utils.Matrix;
import java.util.ArrayList;
import java.util.HashMap;

public class DomainHetesimController {
    Matrix probAuthorPaper;
    Matrix probPaperAuthor;
    Matrix probTermPaper;
    Matrix probPaperTerm;
    Matrix probConferencePaper;
    Matrix probPaperConference;

    Matrix auxiliarObjectL;
    Matrix auxiliarObjectR;

    public DomainHetesimController(Matrix authorPaper, Matrix paperAuthor, Matrix termPaper, Matrix paperTerm, Matrix conferencePaper, Matrix paperConference) {
        probAuthorPaper = authorPaper.normalize();
        probPaperAuthor = paperAuthor.normalize();
        probTermPaper = termPaper.normalize();
        probPaperTerm = paperTerm.normalize();
        probConferencePaper = conferencePaper.normalize();
        probPaperConference = paperConference.normalize();

        auxiliarObjectL = new Matrix();
        auxiliarObjectR = new Matrix();
    }

    private Matrix findMatrix(char source, char target){
        if(source == 'A') return probAuthorPaper;
        if(source == 'T') return probTermPaper;
        if(source == 'C') return probConferencePaper;
        if(target == 'A') return probPaperAuthor;
        if(target == 'T') return probPaperTerm;
        return probPaperConference;
    }

    private Matrix findMatrix(char source, char target, char side){
        if(source == 'A') return probAuthorPaper;
        if(source == 'T') return probTermPaper;
        if(source == 'C') return probConferencePaper;
        if(target == 'A') return probPaperAuthor;
        if(target == 'T') return probPaperTerm;
        if(target == 'C') return probPaperConference;
        if(side == 'l') return auxiliarObjectL;
        return auxiliarObjectR;
    }

    public Matrix heteSim(String path) {
        String pl, pr;
        //create auxiliary matrices (if needed) and split pl/pr
        if (path.length() % 2 == 0) {
            int mid = path.length() / 2;
            char in = path.charAt(mid-1);
            char out = path.charAt(mid);
            generateE(in, out, auxiliarObjectL, auxiliarObjectR);
            pl = path.substring(0, mid);
            pl = pl.concat("E");
            pr = "E";
            pr = pr.concat(path.substring(mid));
        }
        else{
            int mid = path.length();
            pl = path.substring(0, mid+1);
            pr = path.substring(mid);
        }
        String pri = new StringBuilder(pr).reverse().toString(); //inverted path
        //PMpl
        Matrix pmPl = findMatrix(pl.charAt(0), pl.charAt(1), 'l');
        for(int i = 2; i < pl.length(); ++i) pmPl = pmPl.multiply(findMatrix(pl.charAt(i-1), pl.charAt(i), 'l'));
        //PMpri
        Matrix pmPri = findMatrix(pri.charAt(0), pri.charAt(1), 'r');
        for(int i = 2; i < pri.length(); ++i) pmPri = pmPri.multiply(findMatrix(pri.charAt(i-1), pri.charAt(i), 'r'));
        Matrix result = pmPl.multiply(pmPri.transpose());
        //TODO


        ArrayList<Integer> rows = pmPl.rows();
        ArrayList<Integer> columns = pmPri.rows();
        Double rmod = 0.0;
        Double cmod = 0.0;
        for(Integer row : rows){
            rmod = pmPl.modulus(row);
            for(Integer column : columns){
                cmod = pmPri.modulus(column);
                result.addValue(row, column, result.getValue(row, column)/(rmod*cmod));
            }
        }
        return result;
    }

    private void generateE(char source, char target, Matrix matrixL, Matrix matrixR){
        Matrix middleMatrix = findMatrix(source, target);
        //count links
        /*int links = 0;
        for(Integer row : middleMatrix.rows()){
            links += middleMatrix.colums(row).size();
        }*/
        matrixL = new Matrix();
        matrixR = new Matrix();
        //add links
        int c = 0;
        for(int row : middleMatrix.rows()){
            for(int column : middleMatrix.columns(row)){

            }
        }
        for(int i = 0; i < middleMatrix.length; ++i){
            for(int j = 0; j < middleMatrix[0].length; ++j){
                if(middleMatrix[i][j] != 0){
                    matrixL[i][c] = middleMatrix[i][j];
                    matrixR[j][c] = 1; //not yet normalized
                }
            }
        }
        matrixR = normalizeMatrix(matrixR); //normalize
    }
}


