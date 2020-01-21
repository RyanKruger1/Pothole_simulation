/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesimulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Ryan
 */
public class SimulationFrame extends JPanel implements ActionListener {

    static int currentPos = 0;
    static int currentY = 0;

    static int carX = 115;
    static int carY = 500;
    static int carWidth = 0;

    static int holeStartY = 0;
    static int holeStartX = 0;
    static int holeEndX = 0;

    static int currentWidth = 100;
    static int currentLenght = 90;
    static boolean holeDetected = false;

    static ImageIcon images[] = null;

    static ArrayList<String> sensors = new ArrayList<>();

    public void setWidth(int width) {
        currentWidth = width;

    }

    public void setLength(int leng) {
        currentLenght = leng;
    }

    public int getCurrentWidth() {
       return currentWidth;
    }

    public int getCurrentLength() {
       return currentLenght;
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        ImageIcon image = new ImageIcon(System.getProperty("user.dir")+"\\Pothole Detector\\Car_1.png");
        carWidth = image.getIconWidth();
        boolean sense = false;
        if (holeStartY > carY && holeStartY - currentLenght < carY + image.getIconHeight()) {
            image = new ImageIcon(System.getProperty("user.dir")+"\\Pothole Detector\\Car_1_Sensor.png");
            sense = true;

        } else {
            image = new ImageIcon(System.getProperty("user.dir")+"\\Pothole Detector\\Car_1.png");
            sense = false;
        }
        images = new ImageIcon[5];

        images[0] = new ImageIcon(System.getProperty("user.dir")+"\\Pothole Detector\\Road_1.png");
        images[1] = new ImageIcon(System.getProperty("user.dir")+"\\Pothole Detector\\Road_2.png");
        images[2] = new ImageIcon(System.getProperty("user.dir")+"\\Pothole Detector\\Road_3.png");

        g.drawImage(images[currentPos].getImage(), 120, 0, this);

        double centerRoad = image.getIconWidth() / 2;
        double semetricMiddle = centerRoad + 120;

        holeStartY = currentY + currentLenght;
        g.drawImage(image.getImage(), carX, carY, this);
        
        createTheHole(g, 175, currentY, currentWidth, currentLenght, semetricMiddle);

        if (sense) {
            createGrid(g);
        }
    }

    public void createTheHole(Graphics g, int x, int y, int width, int height, double semetricMiddle) {

        double centeredWidth = width / 2;
        double middle = semetricMiddle - centeredWidth;

        int startPoint = (int) middle;
        g.setColor(Color.GRAY);
        g.fillOval(startPoint + Frame.movement, y, width, height);
        g.setColor(Color.BLACK);

        holeStartX = startPoint + Frame.movement;
        holeEndX = startPoint +Frame.movement+ currentWidth;

    }

    public void actionPerformed(ActionEvent e) {

        currentPos++;
        currentY = currentY + 5;

        Frame.JTA.setText("Results:\n");

        if (currentPos >= 3) {
            
            currentPos = 0;
        
        }

        if (currentY > 1000) {
            
            currentY = 0;
            Frame.JTA.setText("Results:");
            sensors.clear();
            holeDetected = false;
        }

        try {
            Stream<String> dis = sensors.stream().distinct();
            Object distinct[] = dis.toArray();

            for (Object d : distinct) {

                Frame.JTA.append("Sensor Triggered:\t" + d.toString() + "\n");

            }
            if (holeDetected) {
                Frame.JTA.append("----- Hole Detected -----\n");
            } else {
                Frame.JTA.append("----- No Hole Detected -----\n");
            }

        } catch (Exception ex) {

        }

        repaint();
    }

    public void createGrid(Graphics g) {
//        int amount = 9;

        int intervalX = Math.round((carWidth - 20) / 3);
        g.setColor(Color.BLUE);

        int Col1 = carX + 25;
        int Col2 = carX + 60;
        int Col3 = carX + 100;

        int Row1 = carY + 20;
        int Row2 = carY + 60;
        int Row3 = carY + 100;

        g.setColor(Color.WHITE);

        g.drawString("A", Col1, carY);
        g.drawString("B", Col2, carY);
        g.drawString("C", Col3, carY);
        
        g.drawString("1", carX, Row1);
        g.drawString("2", carX, Row2);
        g.drawString("3", carX, Row3);

        g.setColor(Color.BLUE);

        // ----------------------------- this is the configuraition for row 1
        int sensorsActivedRow1 = 0;
        int sensorsActiveCol1 = 0;
        int sensorsActiveCol2 = 0;
        int sensorsActiveCol3 = 0;

        if (Col1 + 5 > holeStartX && Col1 + 5 < holeEndX && holeStartY > Row1 && (holeStartY - currentLenght) < Row1) {
            g.setColor(Color.red);
            sensorsActivedRow1++;
            sensorsActiveCol1++;
            sensors.add("A1");
        }

        g.fillOval(Col1, Row1, 10, 10);
        g.setColor(Color.BLUE);

        if (Col2 + 5 >= holeStartX && Col2 + 5 < holeEndX && holeStartY > Row1 && (holeStartY - currentLenght) < Row1) {
            g.setColor(Color.red);
            sensorsActivedRow1++;
            sensorsActiveCol2++;
            sensors.add("B1");
        }

        g.fillOval(Col2, Row1, 10, 10);
        g.setColor(Color.BLUE);

        if (Col3 + 5 > holeStartX && Col3 + 5 < holeEndX && holeStartY > Row1 && (holeStartY - currentLenght) < Row1) {
            g.setColor(Color.red);
            sensorsActivedRow1++;
            sensorsActiveCol3++;
            sensors.add("C1");
        }

        g.fillOval(Col3, Row1, 10, 10);

        g.setColor(Color.BLUE);

        // ----------------------------- this is the configuraition for row 2
        int sensorsActivedRow2 = 0;
        if (Col1 + 5 > holeStartX && Col1 + 5 < holeEndX && holeStartY > Row2 && (holeStartY - currentLenght) < Row2) {
            g.setColor(Color.red);
            sensorsActivedRow2++;
            sensorsActiveCol1++;
            sensors.add("A2");
        }

        g.fillOval(Col1, Row2, 10, 10);
        g.setColor(Color.BLUE);

        if (Col2 + 5 >= holeStartX && Col2 + 5 < holeEndX && holeStartY > Row2 && (holeStartY - currentLenght) < Row2) {
            g.setColor(Color.red);
            sensorsActivedRow2++;
            sensorsActiveCol2++;
            sensors.add("B2");
        }

        g.fillOval(Col2, Row2, 10, 10);

        g.setColor(Color.BLUE);

        if (Col3 + 5 >= holeStartX && Col3 + 5 < holeEndX && holeStartY > Row2 && (holeStartY - currentLenght) < Row2) {
            g.setColor(Color.red);
            sensorsActivedRow2++;
            sensorsActiveCol3++;
            sensors.add("C2");
        }

        g.fillOval(Col3, Row2, 10, 10);

        g.setColor(Color.BLUE);

        // ----------------------------- this is the configuraition for row 3
        int sensorsActivedRow3 = 0;

        if (Col1 + 5 >= holeStartX && Col1 + 5 < holeEndX && holeStartY > Row3 && (holeStartY - currentLenght) < Row3) {
            g.setColor(Color.red);
            sensorsActivedRow3++;
            sensorsActiveCol1++;
            sensors.add("A3");
        }
        g.fillOval(Col1, Row3, 10, 10);
        g.setColor(Color.BLUE);

        if (Col2 + 5 >= holeStartX && Col2 + 5 < holeEndX && holeStartY > Row3 && (holeStartY - currentLenght) < Row3) {
            g.setColor(Color.red);
            sensorsActivedRow3++;
            sensorsActiveCol2++;
            sensors.add("B3");
        }
        g.fillOval(Col2, Row3, 10, 10);
        g.setColor(Color.BLUE);

        if (Col3 + 5 >= holeStartX && Col3 + 5 < holeEndX && holeStartY > Row3 && (holeStartY - currentLenght) < Row3) {
            g.setColor(Color.red);
            sensorsActivedRow3++;
            sensorsActiveCol1++;
            sensors.add("C3");
        }
        g.fillOval(Col3, Row3, 10, 10);
        g.setColor(Color.BLUE);
//
        if (sensorsActivedRow1 > 1 || sensorsActivedRow2 > 1 || sensorsActivedRow3 > 1 || sensorsActiveCol1 > 1 || sensorsActiveCol2 > 1 || sensorsActiveCol3 > 1) {
            holeDetected = true;

            sensors.stream().distinct();
            for (String L : sensors) {
                System.out.println(L);
            }
        }
    }

    public void drawRowOfGrid() {

    }

}
