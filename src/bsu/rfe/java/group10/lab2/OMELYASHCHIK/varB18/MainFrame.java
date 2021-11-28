package bsu.rfe.java.group10.lab2.OMELYASHCHIK.varB18;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    // Константы с исходным размером окна приложения
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    // Объект диалогового окна для выбора файлов.
    // Компонент не создается изначально, т.к. может и не понадобиться
    // пользователю если тот не собирается сохранять данные в файл
    private JFileChooser fileChooser = null;

    // Элементы меню вынесены в поля данных класса, так как ими необходимо
    // манипулировать из разных мест
    private JMenuItem saveToTextMenuItem;
    private  JMenuItem saveToBinMenuItem;
    private JMenuItem searchValueMenuItem;
    // Поля ввода для считывания значений переменных
    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;
    private Box hBoxResult; //
    // Визуализатор ячеек таблицы
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    // модель данных с результами вычислений
    private GornerTableModel data;
    private Double[] coefficients;
    public MainFrame(Double[] coefficients) {
        super("Табулирование функции на отрезке");
        this.coefficients = coefficients;
        setSize(WIDTH,HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2, // Отцентрировать окно приложения на экране
                (kit.getScreenSize().height - HEIGHT)/2);

        JMenuBar menuBar = new JMenuBar();  //Создать полосу меню и установить ее в верхнюю часть фрейма
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        JMenu tableMenu = new JMenu("Таблица");
        menuBar.add(tableMenu);

        JMenu SpravkaMenu = new JMenu("Справка");
        menuBar.add(SpravkaMenu);
        // Создать новое "действие" по сохранению в текстовый файл
        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
            public void actionPerformed(ActionEvent event) {
                // Если экземпляр диалогового окна "Открыть файл" еще не создан,
                // то создать его и инициализировать текущей директорией
                if (fileChooser==null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }

                // Показать диалоговое окно
                if (fileChooser.showSaveDialog(MainFrame.this) ==
                        JFileChooser.APPROVE_OPTION)
                    saveToTextFile(new File(fileChooser.getSelectedFile().getName().concat(".txt")));
            }
        };
        Action saveToBinAction = new AbstractAction("Сохранить в бинарный файл") {
            public void actionPerformed(ActionEvent event) {
                // Если экземпляр диалогового окна "Открыть файл" еще не создан,
                // то создать его и инициализировать текущей директорией
                if (fileChooser==null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }

                // Показать диалоговое окно
                if (fileChooser.showSaveDialog(MainFrame.this) ==
                        JFileChooser.APPROVE_OPTION)
                    saveToBinFile(new File(fileChooser.getSelectedFile().getName().concat(".bin")));
            }
        };

//Кнопочки
        saveToBinMenuItem = fileMenu.add(saveToBinAction);
        saveToBinMenuItem.setEnabled(false);
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        saveToTextMenuItem.setEnabled(false);
        Action aboutProgrammAction = new AbstractAction("О программе") {
            public void actionPerformed(ActionEvent event) {
                JLabel info = new JLabel("Подготовил: Омелящик Иван Андреевич, 2 курс 10 группа");
                JOptionPane.showMessageDialog(MainFrame.this, info,
                        "О программе", JOptionPane.PLAIN_MESSAGE);
            }
        };
          SpravkaMenu.add(aboutProgrammAction);
        Action searchValueAction = new AbstractAction("Найти значение функции") {
            public void actionPerformed(ActionEvent event) {
                // Запросить пользователя ввести искомую строку
                String value = JOptionPane.showInputDialog(MainFrame.this,
                        "Введите значение для поиска", "Поиск значения",
                        JOptionPane.QUESTION_MESSAGE);
                // Установить введенное значение в качестве иголки
                renderer.setNeedle(value);
                // Обновить таблицу
                getContentPane().repaint();
            }
        };
        searchValueMenuItem = tableMenu.add(searchValueAction);
        searchValueMenuItem.setEnabled(false);
        ////
        textFieldFrom = new JTextField("0.0", 10);
        // Установить максимальный размер равный предпочтительному, чтобы
        // предотвратить увеличение размера поля ввода
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());

        textFieldTo = new JTextField("1.0", 10);
        textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());

        // Создать поле для ввода шага по X
        textFieldStep = new JTextField("0.1", 10);
        textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());

        // Создать контейнер типа "коробка с горизонтальной укладкой"
        Box hboxXRange = Box.createHorizontalBox();
        //hboxXRange.setBorder(BorderFactory.createTitledBorder(
        //      BorderFactory.createEtchedBorder(), "Настройки:"));
        // Создать новое действие по поиску значений функции
        hboxXRange.add(Box.createHorizontalGlue());
        hboxXRange.add(new JLabel("X начальное:"));
        hboxXRange.add(Box.createHorizontalStrut(10));
        hboxXRange.add(textFieldFrom);
        hboxXRange.add(Box.createHorizontalStrut(20));
        hboxXRange.add(new JLabel("X конечное:"));
        hboxXRange.add(Box.createHorizontalStrut(10));
        hboxXRange.add(textFieldTo);
        hboxXRange.add(Box.createHorizontalStrut(20));
        hboxXRange.add(new JLabel("Шаг для X:"));
        hboxXRange.add(Box.createHorizontalStrut(10));
        hboxXRange.add(textFieldStep);
        hboxXRange.add(Box.createHorizontalGlue());
        hboxXRange.setPreferredSize(new Dimension(
                (int) hboxXRange.getMaximumSize().getWidth(),
                (int) hboxXRange.getMinimumSize().getHeight() * 2));

        add(hboxXRange, BorderLayout.NORTH);
        hBoxResult = Box.createHorizontalBox();
        hBoxResult.add(new JPanel());
        add(hBoxResult, BorderLayout.CENTER);
        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double from = Double.parseDouble(textFieldFrom.getText());
                    Double to = Double.parseDouble(textFieldTo.getText());
                    Double step = Double.parseDouble(textFieldStep.getText());

                    if (from > to) {
                        double tmp = from;
                        from = to;
                        to = tmp;
                        textFieldFrom.setText(String.valueOf(from));
                        textFieldFrom.setText(String.valueOf(to));
                    }

                    if (step <= 0) {
                        throw new NumberFormatException();
                    }

                    data  = new GornerTableModel(coefficients, from, to,step);
                    JTable table = new JTable(data);
                    table.setDefaultRenderer(Double.class, renderer);
                    table.setRowHeight(30);
                    hBoxResult.removeAll();
                    hBoxResult.add(new JScrollPane(table));
                    validate();

                    saveToTextMenuItem.setEnabled(true);
                    saveToBinMenuItem.setEnabled(true);
                    searchValueMenuItem.setEnabled(true);
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Неверные данные", "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        JButton resetButton = new JButton("Очистить");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldFrom.setText("0.0");
                textFieldTo.setText("1.0");
                textFieldStep.setText("0.1");
                hBoxResult.removeAll();
                hBoxResult.add(new JPanel());
                saveToTextMenuItem.setEnabled(false);
                saveToBinMenuItem.setEnabled(false);
                searchValueMenuItem.setEnabled(false);
                validate();
            }
        });
        Box hBoxButtons = Box.createHorizontalBox();
        //hBoxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hBoxButtons.add(Box.createHorizontalGlue());
        hBoxButtons.add(buttonCalc);
        hBoxButtons.add(Box.createHorizontalStrut(30));
        hBoxButtons.add(resetButton);
        hBoxButtons.add(Box.createHorizontalGlue());
        hBoxButtons.setPreferredSize(new Dimension(
                (int) hBoxButtons.getMaximumSize().getWidth(),
                (int) hBoxButtons.getMinimumSize().getHeight() * 2
        ));

        add(hBoxButtons, BorderLayout.SOUTH);
    }
    //ЗАКОНЧИЛСЯ НАШ КОНСТРУКТОК


    protected void saveToTextFile(File selectedFile) {
        try {
            // Создать новый символьный поток вывода, направленный в  указанный файл
            PrintStream out = new PrintStream(selectedFile);

            // Записать в поток вывода заголовочные сведения
            out.println("Результаты табулирования функции:");
            out.println("");
            out.println("Интервал от " + data.getFrom() + " до " + data.getTo() +
                    " с шагом " + data.getStep());
            out.println("====================================================");

            // Записать в поток вывода значения в точках
            for (int i = 0; i<data.getRowCount(); i++)
                out.println("Значение в точке " + data.getValueAt(i,0)  + " равно " +
                        data.getValueAt(i,1));

            // Закрыть поток
            out.close();
        } catch (FileNotFoundException e) {
            // Исключительную ситуацию "ФайлНеНайден" можно не
            // обрабатывать, так как мы файл создаем, а не открываем
        }
    }
    void saveToBinFile(File file) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            for (int i = 0; i < data.getRowCount(); i++) {
                out.writeDouble((Double) data.getValueAt(i, 0));
                out.writeDouble((Double) data.getValueAt(i, 1));
            }
            out.close();
        } catch (Exception ignore) {

        }
    }

}
