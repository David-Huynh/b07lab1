public class Polynomial {
    double [] coeff;

    public Polynomial(){
        coeff = new double[0];
    }

    public Polynomial(double [] init_coeff) {
        coeff = init_coeff;
    }

    // Assumes polynomials are always ordered by increasing power and that even coeff of 0 are counted
    public Polynomial add(Polynomial b) {
        int maxLength = Math.max(coeff.length, b.coeff.length);
        double [] newCoeff = new double[maxLength];
        for (int i = 0; i < maxLength; i++){
            if (i < coeff.length)
                newCoeff[i] += coeff[i];
            if (i < b.coeff.length)
                newCoeff[i] += b.coeff[i];
        }
        return new Polynomial(newCoeff);
    }

    public double evaluate(double x) {
        double eval = 0;
        for (int i = 0; i < coeff.length; i++) {
            eval += coeff[i]*Math.pow(x,i);
        }
        return eval;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

}
