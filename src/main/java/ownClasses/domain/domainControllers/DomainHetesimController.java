package main.java.ownClasses.domain.domainControllers;

import main.java.sharedClasses.utils.Matrix;

public class DomainHetesimController {
    private Matrix probAuthorPaper;
    private Matrix probPaperAuthor;
    private Matrix probTermPaper;
    private Matrix probPaperTerm;
    private Matrix probConferencePaper;
    private Matrix probPaperConference;

    private Matrix auxiliarObjectL;
    private Matrix auxiliarObjectR;

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

    public Matrix heteSim(String path) {
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
        //PMpl
        Matrix pmPl = findMatrix(pl.charAt(0), pl.charAt(1), 'l');
        for (int i = 2; i < pl.length(); ++i) pmPl = pmPl.multiply(findMatrix(pl.charAt(i - 1), pl.charAt(i), 'l'));
        //PMpri
        Matrix pmPri = findMatrix(pri.charAt(0), pri.charAt(1), 'r');

        for (int i = 2; i < pri.length(); ++i)
            pmPri = pmPri.multiply(findMatrix(pri.charAt(i - 1), pri.charAt(i), 'r'));
        Matrix result = pmPl.multiply(pmPri.transpose());

        Double rmod = 0.0;
        Double cmod = 0.0;
        for (Integer row : result.rows()) {
            rmod = pmPl.rowModulus(row);
            for (Integer column : result.columns(row).keySet()) {
                cmod = pmPri.rowModulus(column);
                result.addValue(row, column, result.getValue(row, column) / (rmod * cmod));
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
            for (int column : middleMatrix.columns(row).keySet()) {
                auxiliarObjectL.addValue(row, i, middleMatrix.getValue(row, column));
                auxiliarObjectR.addValue(i, column, 1.0);
                i += 1;
            }
        }
        auxiliarObjectR = auxiliarObjectR.transpose().normalize();
    }
}


