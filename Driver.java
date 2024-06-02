import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Driver {
    public static void main(String[] args) throws IOException {
        // TEST evaluate
        System.out.println("Starting evaluate tests:");
        double[] c = { -1.1, -2.2, 3.3 };
        int[] e = { 0, 2, 4 };
        Polynomial p = new Polynomial(c, e);
        System.out.println(p.evaluate(3));
        System.out.println("---Ending evaluate tests---");

        // TEST hasRoot
        System.out.println("Starting hasRoot tests:");
        System.out.println(p.evaluate(1));
        System.out.println(p.hasRoot(1));
        System.out.println("---Ending hasRoot tests---");

        // TEST adding with duplicate exponents summing to 0, gaps, and exponents to the
        // power of 0 and 1
        System.out.println("Starting adding tests:");
        double[] c1 = { 6, 2, 2, 5, -3 };
        int[] e1 = { 1, 0, 0, 3, 8 };
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = { 3, -2, -3, 4, -9 };
        int[] e2 = { 1, 3, 3, 4, 8 };
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        assert Arrays.equals(s.coeff, new double[] { 4, 9, 4, -12 });
        assert Arrays.equals(s.exponents, new int[] { 0, 1, 4, 8 });
        System.out.println("---Ending adding tests---");

        // TEST filereader with leading negative, inner negative, normal positive and
        // gap between exponents and a zero exponent and a exponent to the power of 1
        System.out.println("Starting filereader tests:");
        Polynomial p3 = new Polynomial(new File("./text.txt"));
        System.out.println(Arrays.toString(p3.coeff));
        System.out.println(Arrays.toString(p3.exponents));

        assert Arrays.equals(p3.coeff, new double[] { -5, -3.3, 7.2 });
        assert Arrays.equals(p3.exponents, new int[] { 0, 1, 8 });
        System.out.println("---Ending filereader tests---");

        // TEST multiply
        System.out.println("Starting multiply tests:");
        double[] c3 = { 1, 2, -3 };
        int[] e3 = { 1, 2, 3 };
        p1 = new Polynomial(c3, e3);
        double[] c4 = { 1, 2, 3 };
        int[] e4 = { 0, 2, 3 };
        p2 = new Polynomial(c4, e4);
        p3 = p1.multiply(p2);
        System.out.println(Arrays.toString(p3.coeff));
        System.out.println(Arrays.toString(p3.exponents));
        assert Arrays.equals(p3.coeff, new double[] { 1, 2, -1, 7, -9 });
        assert Arrays.equals(p3.exponents, new int[] { 1, 2, 3, 4, 6 });
        System.out.println("---Ending multiply tests---");

        // TEST saveToFile
        System.out.println("Starting saveToFile tests:");
        Polynomial p4 = new Polynomial(new double[] { 1, -2.2, 3.3 }, new int[] { 0, 1, 5 });
        p4.saveToFile("test.txt");
        BufferedReader br = new BufferedReader(new FileReader("test.txt"));
        String line = br.readLine();
        System.out.println(line);
        br.close();
        assert line.equals("1.0-2.2x3.3x5");
        System.out.println("---Ending saveToFile tests---");
    }
}
