package main.java.ownClasses.domain.domainControllers;

import main.java.ownClasses.utils.Matrix;

import java.util.ArrayList;

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

        auxiliarObjectL = new Matrix;
        auxiliarObjectR = new Matrix;
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

    public static double modulus(ArrayList<Pair<Integer, Double>> v){
        double sum = 0;
        for (Pair<Integer, Double> aV : v) {
            sum += aV.second*aV.second;
        }
        return Math.sqrt(sum);
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
        ArrayList<Pair<Integer, Double>> pla, prb;
        //TODO
        for(int i = 0; i < result.length; ++i){
            pla = pmPl[i];
            for(int j = 0; j < result[0].length; ++j){
                prb = pmPri[j];
                result[i][j] /= (modulus(pla)*modulus(prb));
            }
        }
        return result;
    }

    private void generateE(char source, char target, double[][] matrixL, double[][] matrixR){
        double[][] middleMatrix = findMatrix(source, target);
        //count links
        int links = 0;
        for (double[] aMiddleMatrix : middleMatrix) {
            for (double e : aMiddleMatrix) {
                if(e != 0) links++;
            }
        }
        matrixL = new double[middleMatrix.length][links];
        matrixR = new double[middleMatrix[0].length][links];
        //add links
        int c = 0;
        for(int i = 0; i < middleMatrix.length; ++i){
            for(int j = 0; j < middleMatrix[0].length; ++i){
                if(middleMatrix[i][j] != 0){
                    matrixL[i][c] = middleMatrix[i][j];
                    matrixR[j][c] = 1; //not yet normalized
                }
            }
        }
        matrixR = normalizeMatrix(matrixR); //normalize
    }
}


