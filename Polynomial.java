import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    double[] coeff;
    int[] exponents;

    public Polynomial() {
        coeff = new double[1];
        exponents = new int[1];
    }

    public Polynomial(double[] init_coeff, int[] init_exponents) {
        coeff = init_coeff;
        exponents = init_exponents;
    }

    public Polynomial(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        br.close();
        String terms[] = line.split("(\\+|(?=-))");

        double coeffs[] = new double[terms.length];
        int exponents[] = new int[terms.length];

        for (int i = 0; i < terms.length; i++) {
            String strNum[] = terms[i].split("x");
            if (strNum.length == 1) {
                if (terms[i].charAt(0) == 'x') {
                    coeffs[i] = 1;
                    exponents[i] = Integer.parseInt(strNum[0]);
                } else if (terms[i].indexOf('x') != -1) {
                    exponents[i] = 1;
                } else {
                    exponents[i] = 0;
                }
                coeffs[i] = Double.parseDouble(strNum[0]);
            } else {
                coeffs[i] = Double.parseDouble(strNum[0]);
                exponents[i] = Integer.parseInt(strNum[1]);
            }
        }

        this.coeff = coeffs;
        this.exponents = exponents;
    }

    public Polynomial add(Polynomial b) {
        // Adding
        double newCoeff[] = new double[Math.max(maxOfExpo() + 1, b.maxOfExpo() + 1)];
        for (int i = 0; i < exponents.length; i++) {
            newCoeff[exponents[i]] += coeff[i];
        }
        for (int i = 0; i < b.exponents.length; i++) {
            newCoeff[b.exponents[i]] += b.coeff[i];
        }

        // Removing 0's
        int numExpo = 0;
        for (double coeff : newCoeff) {
            if (coeff != 0) {
                numExpo++;
            }
        }
        double nonZeroNewCoeff[] = new double[numExpo];
        int newExpo[] = new int[numExpo];
        int expoCounter = 0;
        for (int i = 0; i < newCoeff.length; i++) {
            if (newCoeff[i] != 0) {
                newExpo[expoCounter] = i;
                nonZeroNewCoeff[expoCounter] = newCoeff[i];
                expoCounter++;
            }
        }
        return new Polynomial(nonZeroNewCoeff, newExpo);
    }

    public Polynomial multiply(Polynomial p) {
        // 1 Term Multiplication
        if (coeff.length == 1) {
            Polynomial p2 = new Polynomial(new double[p.coeff.length], new int[p.coeff.length]);
            for (int i = 0; i < p.coeff.length; i++) {
                p2.coeff[i] = coeff[0] * p.coeff[i];
                p2.exponents[i] = exponents[0] + p.exponents[i];
            }
            return p2;
        }
        // Multiterm Recursive to 1 term
        Polynomial temp = new Polynomial(new double[] { coeff[0] }, new int[] { exponents[0] });
        Polynomial sum = temp.multiply(p);
        for (int i = 1; i < coeff.length; i++) {
            temp = new Polynomial(new double[] { coeff[i] }, new int[] { exponents[i] });
            sum = sum.add(temp.multiply(p));
        }
        return sum;
    }

    public int maxOfExpo() {
        int max = 0;
        for (int exponent : exponents) {
            if (max < exponent)
                max = exponent;
        }
        return max;
    }

    public double evaluate(double x) {
        double eval = 0;
        for (int i = 0; i < coeff.length; i++) {
            eval += coeff[i] * Math.pow(x, exponents[i]);
        }
        return eval;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public void saveToFile(String fileName) throws IOException {
        String line = "";
        for (int i = 0; i < coeff.length; i++) {
            if (exponents[i] == 1)
                line = line + coeff[i] + "x";
            else if (exponents[i] != 0)
                line = line + coeff[i] + "x" + exponents[i];
            else
                line = line + coeff[i];
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(line);
        bw.close();
    }
}
