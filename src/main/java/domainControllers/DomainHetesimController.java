package main.java.domainControllers;

public class DomainHetesimController {
    double[][] probAuthorPaper;
    double[][] probPaperAuthor;
    double[][] probTermPaper;
    double[][] probPaperTerm;
    double[][] probConferencePaper;
    double[][] probPaperConference;

    public DomainHetesimController(int[][] authorPaper, int[][] paperAuthor, int[][] termPaper, int[][] paperTerm, int[][] conferencePaper, int[][] paperConference) {
        probAuthorPaper = normalizeMatrix(authorPaper);
        probPaperAuthor = normalizeMatrix(paperAuthor);
        probTermPaper = normalizeMatrix(termPaper);
        probPaperTerm = normalizeMatrix(paperTerm);
        probConferencePaper = normalizeMatrix(conferencePaper);
        probPaperConference = normalizeMatrix(paperConference);
    }

    private double[][] normalizeMatrix(int[][] matrix) {
        double[][] normalized = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            int t = 0;
            for (int j = 0; j < matrix[0].length; ++j) t += matrix[i][j];
            if(t > 0) {
                for (int j = 0; j < matrix[0].length; ++j) normalized[i][j] = (double) matrix[i][j] / t;
            }
        }
        return normalized;
    }

    private double[][] normalizeMatrix(double[][] matrix) {
        double[][] normalized = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            int t = 0;
            for (int j = 0; j < matrix[0].length; ++j) t += matrix[i][j];
            if(t > 0) {
                for (int j = 0; j < matrix[0].length; ++j) normalized[i][j] = matrix[i][j] / t;
            }
        }
        return normalized;
    }

    private double[][] transpose(double [][] m){
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    public double[][] heteSim(String path) {
        double[][] auxiliarObjectL = new double[0][0];
        double[][] auxiliarObjectR = new double[0][0];
        if (path.length() % 2 == 0) {
            int mid = path.length() / 2;
            char in = path.charAt(mid);
            char out = path.charAt(mid + 1);
            generateE(in, out, auxiliarObjectL, auxiliarObjectR);
            int f = auxiliarObjectR.length;
        }
    }

    private void generateE(char in, char out, double[][] matrixL, double[][] matrixR){
        double[][] middleMatrix;
        if(in == 'A') middleMatrix = probAuthorPaper;
        else if(in == 'T') middleMatrix = probTermPaper;
        else if(in == 'C') middleMatrix = probConferencePaper;
        else if(out == 'A') middleMatrix = probPaperAuthor;
        else if(out == 'T') middleMatrix = probPaperTerm;
        else middleMatrix = probPaperConference;
        //count links
        int links = 0;
        for (double[] aMiddleMatrix : middleMatrix) {
            for (double e : aMiddleMatrix) {
                if(e != 0) links++;
            }
        }
        matrixL = new double[middleMatrix.length][links];
        matrixR = new double[middleMatrix[0].length][links]; //needs to be transposed
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
        matrixR = transpose(normalizeMatrix(matrixR)); //transpose and normalize
    }
}


