package main.java.domainControllers;

public class DomainHetesimController {
    double[][] probAuthorPaper;
    double[][] probPaperAuthor;
    double[][] probTermPaper;
    double[][] probPaperTerm;
    double[][] probConferencePaper;
    double[][] probPaperConference;

    double[][] auxiliarObjectL;
    double[][] auxiliarObjectR;

    public DomainHetesimController(int[][] authorPaper, int[][] paperAuthor, int[][] termPaper, int[][] paperTerm, int[][] conferencePaper, int[][] paperConference) {
        probAuthorPaper = normalizeMatrix(authorPaper);
        probPaperAuthor = normalizeMatrix(paperAuthor);
        probTermPaper = normalizeMatrix(termPaper);
        probPaperTerm = normalizeMatrix(paperTerm);
        probConferencePaper = normalizeMatrix(conferencePaper);
        probPaperConference = normalizeMatrix(paperConference);

        auxiliarObjectL = new double[0][0];
        auxiliarObjectR = new double[0][0];
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
            for (int j = 0; j < m[0].length; j++) temp[j][i] = m[i][j];
        return temp;
    }

    private double[][] findMatrix(char source, char target){
        if(source == 'A') return probAuthorPaper;
        if(source == 'T') return probTermPaper;
        if(source == 'C') return probConferencePaper;
        if(target == 'A') return probPaperAuthor;
        if(target == 'T') return probPaperTerm;
        return probPaperConference;
    }

    private double[][] findMatrix(char source, char target, char side){
        if(source == 'A') return probAuthorPaper;
        if(source == 'T') return probTermPaper;
        if(source == 'C') return probConferencePaper;
        if(target == 'A') return probPaperAuthor;
        if(target == 'T') return probPaperTerm;
        if(target == 'C') return probPaperConference;
        if(side == 'l') return auxiliarObjectL;
        return auxiliarObjectR;
    }

    // return C = A * B
    public static double[][] multiply(double[][] A, double[][] B) {
        int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] C = new double[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++) C[i][j] += A[i][k] * B[k][j];
        return C;
    }

    public static double modulus(double[] v){
        double sum = 0;
        for (double aV : v) {
            sum += aV*aV;
        }
        return Math.sqrt(sum);
    }

    public double[][] heteSim(String path) {
        double[][] auxiliarObjectL = new double[0][0];
        double[][] auxiliarObjectR = new double[0][0];
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
        double[][] pmPl = findMatrix(pl.charAt(0), pl.charAt(1), 'l');
        for(int i = 2; i < pl.length(); ++i) pmPl = multiply(pmPl, findMatrix(pl.charAt(i-1), pl.charAt(i), 'l'));
        //PMpri
        double[][] pmPri = findMatrix(pri.charAt(0), pri.charAt(1), 'r');
        for(int i = 2; i < pri.length(); ++i) pmPri = multiply(pmPri, findMatrix(pri.charAt(i-1), pri.charAt(i), 'r'));
        double[][] result = multiply(pmPl, transpose(pmPri));
        double[] pla, prb;
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


