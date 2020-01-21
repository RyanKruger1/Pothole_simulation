package thesimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;

class Frame extends JFrame {

    private JTextField info = new JTextField();
    private JButton startButton = new JButton();
    private JButton stopButton = new JButton();
    private JButton createHole = new JButton();

    private JLabel widthLbl = new JLabel();
    private JLabel lengthLbl = new JLabel();
    private JLabel depthLbl = new JLabel();

    private JButton plus = new JButton();
    private JButton minus = new JButton();

    private JTextField widthField = new JTextField();
    private JTextField lengthField = new JTextField();
    private JTextField depthField = new JTextField();

    private JButton LeftButton = new JButton();
    private JButton RightButton = new JButton();
    public static JTextArea JTA = new JTextArea();

    public static SimulationFrame sf = new SimulationFrame();
    private JPanel Panel1 = new JPanel();

    public String state = "";

    public Frame() {
        //setLayout(new GridLayout(1, 2));
        super("Simulation Project Version 1.2");
        sf.setBounds(0, 0, 500, 1000);
        sf.setBackground(Color.GREEN);
        add(sf);

        Panel1 = new JPanel();
        Panel1.setBounds(500, 0, 500, 1000);
        Panel1.setLayout(null);
        animationHandler aHandler = new animationHandler();
        handler handles = new handler();
        //the default settings of the frame

        startButton = new JButton("Start Simulation");
        stopButton = new JButton("Stop Simulation");
        createHole = new JButton("Create Hole");
        plus = new JButton("Faster");
        minus = new JButton("Slower");

        widthField = new JTextField();
        widthField.setBounds(100, 140, 100, 20);
        lengthField = new JTextField();
        lengthField.setBounds(100, 170, 100, 20);
        depthField = new JTextField();
        depthField.setBounds(100, 200, 100, 20);

        widthLbl = new JLabel("Width:");
        widthLbl.setForeground(Color.WHITE);
        widthField.setText(sf.getCurrentWidth() + "");
        widthLbl.setBounds(50, 140, 100, 20);
        lengthLbl = new JLabel("Length:");
        lengthField.setText(sf.getCurrentLength() + "");
        lengthLbl.setForeground(Color.WHITE);
        lengthLbl.setBounds(50, 170, 100, 20);
        depthLbl = new JLabel("Depth: ");
        depthLbl.setForeground(Color.WHITE);
        depthField.setText("20");
        depthLbl.setBounds(50, 200, 100, 20);

        LeftButton = new JButton("Move Right");
        RightButton = new JButton("Move Left");

        LeftButton.setBounds(300, 145, 100, 40);
        RightButton.setBounds(300, 185, 100, 40);

        LeftButton.addActionListener(aHandler);
        RightButton.addActionListener(aHandler);

        plus.setBounds(120, 280, 100, 40);
        minus.setBounds(120, 330, 100, 40);

        plus.addActionListener(handles);
        minus.addActionListener(handles);

        createHole.setBounds(100, 230, 200, 40);

        startButton.setBounds(100, 20, 200, 40);
        stopButton.setBounds(100, 80, 200, 40);

        startButton.addActionListener(handles);

        stopButton.addActionListener(handles);
        createHole.addActionListener(handles);

//        startButton.setBackground(Color.GREEN);
//
//        stopButton.setBackground(Color.RED);
        JTA = new JTextArea();
        JTA.setBounds(100, 500, 300, 300);
        JTA.setText("Results:");

        JTA.setEditable(false);

        Panel1.add(widthLbl);
        Panel1.add(lengthLbl);
        Panel1.add(depthLbl);

        Panel1.add(startButton);
        Panel1.add(stopButton);
        Panel1.add(createHole);

        Panel1.add(widthField);
        Panel1.add(lengthField);
        Panel1.add(depthField);

        Panel1.add(plus);
        Panel1.add(minus);

        Panel1.add(LeftButton);
        Panel1.add(RightButton);
        Panel1.add(JTA);

        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), "Contol Panel");
        border.setTitleColor(Color.WHITE);
        Panel1.setBorder(border);
        Panel1.setBackground(Color.black);

        add(Panel1);

    }
    int time = 100;
    Timer t = new Timer(time, sf);

    public class handler implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == startButton) {
                t.start();
                state = "start";

            } else if (ev.getSource() == stopButton) {
                t.stop();
                state = "stop";

            } else if (ev.getSource() == createHole) {
                try {

                    int width = Integer.parseInt(widthField.getText());
                    int length = Integer.parseInt(lengthField.getText());

                    sf.setWidth(width);
                    sf.setLength(length);
                    sf.repaint();

                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                } catch (Exception ex) {
                    System.out.println(ex);
                }

            } else if (ev.getSource() == plus) {

                time = time - 10;
                if (time <= 0) {
                    time = 50;
                }
                System.out.println(time);
                t.setDelay(time);

            } else if (ev.getSource() == minus) {

                time = time + 10;

                t.setDelay(time);
            }

        }
    }
    public static int movement = 0;

    public class animationHandler implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == LeftButton) {

                movement = movement - 2;
                sf.repaint();
            } else if (ev.getSource() == RightButton) {
                movement = movement + 2;
                sf.repaint();
            }
        }
    }
}
