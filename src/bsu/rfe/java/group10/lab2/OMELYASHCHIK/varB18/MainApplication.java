package bsu.rfe.java.group10.lab2.OMELYASHCHIK.varB18;
import javax.swing.*;
public class MainApplication {
    public static void main (String[] args) {

        if (args.length == 0) {
            System.out.println("NO ARGUMENTS");
            System.exit(-1);
        }
        Double[] coefficients = new Double[args.length];
        int i = 0;
        try {
            for (String arg : args) {
                coefficients[i++] = Double.parseDouble(arg);
            }
        } catch (NumberFormatException exception) {
            System.out.println("WRONG ARGUMENTS");
            System.exit(-2);
        }
        MainFrame frame = new MainFrame(coefficients);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
