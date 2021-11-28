package bsu.rfe.java.group10.lab2.OMELYASHCHIK.varB18;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.math.BigDecimal;

public class GornerTableCellRenderer implements TableCellRenderer {
    private String needle = null;
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();
    //setter

    public void setNeedle(String needle) {
        this.needle = needle;
    }

    public GornerTableCellRenderer() {
        //максимум знаков после запятой
        formatter.setMaximumFractionDigits(2);
        // Не использовать группировку (не отделять тысячи ни запятыми, ни пробелами)
        formatter.setGroupingUsed(false);
        // Установить в качестве разделителя дробной части точку, а не запятую.
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        // Разместить надпись внутри панели
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT)); // выравнивание по левому краю

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Преобразовать double в строку с помощью форматировщика
        String formattedDouble = formatter.format(value);
        double d = Double.parseDouble(formattedDouble);
        // Установить текст надписи равным строковому представлению числа
        label.setText(formattedDouble);
       String [] a = String.valueOf(d).split("[.]");
       int dTemp = Integer.parseInt(a[0]);
       int dFrac = Integer.parseInt(a[1]);


        if (column == 1 && needle != null && needle.equals(formattedDouble)) {
            // Номер столбца = 1 (т.е. второй столбец) + иголка не null (значит что-то ищем)
            // + значение иголки совпадает со значением ячейки таблицы - окрасить задний фон
            // панели в красный цвет
            panel.setBackground(Color.RED);
        } else {
            // Иначе - в обычный белый
            panel.setBackground(Color.WHITE);
        }
        if (column == 1 && dTemp==dFrac) {
            panel.setBackground(Color.ORANGE);

        }


        return panel;
    }
        public DecimalFormat getFormatter(){
        return formatter;
        }
    }

