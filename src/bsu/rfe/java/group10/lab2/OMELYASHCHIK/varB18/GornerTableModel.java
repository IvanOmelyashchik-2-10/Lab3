package bsu.rfe.java.group10.lab2.OMELYASHCHIK.varB18;
import javax.swing.table.AbstractTableModel;
public class GornerTableModel extends AbstractTableModel {
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 2 ? Boolean.class : Double.class;
    }
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    public  double result  = 0.0;


    public GornerTableModel(Double[] coefficients, Double from, Double to, Double step) {
        this.coefficients = coefficients;
        this.from = from;
        this.to = to;
        this.step = step;

    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public double getStep() {
        return step;
    }


    public int getRowCount() {
        //Вычислить количество значений аргумента исходя из шага
        return (int) (Math.ceil((to-from)/step))+1;
    }

    @Override
    public int getColumnCount() {

        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        double x = from + step * rowIndex;
        //double result = 0.0;
        boolean PALIDROME = false;
        if (columnIndex == 1 ) {
            result = 0.0;
            int i = coefficients.length - 1;
            result = coefficients[i--];
            while (i >= 0) {
                result = result * x + coefficients[i--];
            }
            
        }
        if(columnIndex == 2){
            int palindrome = (int)result; //
            int reverse = 0;

            while (palindrome != 0) {
                int remainder = palindrome % 10;
                reverse = reverse * 10 + remainder;
                palindrome = palindrome / 10;
            }
            if ((int) result == reverse) {
                PALIDROME = true;
            } else {
                PALIDROME = false;
            }

        }


        switch (columnIndex) {
            case 0:
                return x;
            case 1:
                return result;
            case 2:
                return PALIDROME == true ? true : false;
            default:
                return 0;

        }
    }


    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Значение х";
            case 1: return "Значение многочлена по схеме Горнера";
            case 2: return "Целая часть палиндром?";
            default: return "";
        }

    }
}
