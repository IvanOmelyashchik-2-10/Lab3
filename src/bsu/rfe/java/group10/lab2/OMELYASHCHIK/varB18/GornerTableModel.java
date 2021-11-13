package bsu.rfe.java.group10.lab2.OMELYASHCHIK.varB18;
import javax.swing.table.AbstractTableModel;
public class GornerTableModel extends AbstractTableModel {
    public Class<?> getColumnClass(int columnIndex, boolean Flag) {
        return columnIndex == 2 ? Boolean.class : Double.class;
    }
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    private  Double param;

    public GornerTableModel(Double[] coefficients, Double from, Double to, Double step,Double param) {
        this.coefficients = coefficients;
        this.from = from;
        this.to = to;
        this.step = step;
        this.param = param;

    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public Double getParam() {
        return param;
    }

    public int getRowCount() {
        //Вычислить количество значений аргумента исходя из шага
        return new Double(Math.ceil((to-from)/step)).intValue()+1;
    }

    @Override
    public int getColumnCount() {

        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        double x = from + step*rowIndex;
        double y = x - param;
        if (columnIndex == 0) {
            return x;
        }
        else if (columnIndex == 1) {
            return y;
        }
        else {
            return  x < 0;
        }



    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Значение х";
            case 1: return "Значение у";
            case 2: return "x < 0";
        }
        return "";
    }
}
