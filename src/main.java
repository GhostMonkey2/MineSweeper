/**
 * Created by andrewliu on 15-11-22.
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class main {

    boolean isGameOver = false;
    boolean areMineSet = false;
    boolean isFirstClick = true;

    JPanel mainPanel;
    //ArrayList<Boom> booms;
    JFrame theFrame;
    Boom booms[][];

    int numOfMines = 10;
    int rows = 9;
    int cols = 9;


    public static void main(String[] args) {
        main m = new main();
        m.buildGUI();

        if (m.isFirstClick) {
            m.isFirstClick = false;

            //timer begin
            m.setMines();
        }
        m.ripplemines();

    }


    public void buildGUI() {

        theFrame = new JFrame("MineWepper");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        booms = new Boom[9][9];
        mainPanel = new JPanel();

        GridLayout grid = new GridLayout(9, 9);
        mainPanel = new JPanel(grid);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Boom b = new Boom("ic_launcher.png");

                booms[i][j] = b;
                mainPanel.add(b);

            }
        }

        theFrame.getContentPane().add(mainPanel);

        theFrame.setBounds(100,100, 500,500);
        theFrame.setVisible(true);
    }

    public void setMines() {
        Random rand = new Random();
        int mineRow;
        int mineCol;

        for (int i = 0; i < numOfMines; i++) {
            mineRow = rand.nextInt(cols);
            mineCol = rand.nextInt(rows);
            if (mineCol + 1 != cols || mineRow + 1 != rows) {
                if (booms[mineCol + 1][mineRow + 1].hasMine) {
                    mineRow--;
                }
                booms[mineRow+1][mineCol+1].setMine(true);
            } else {
                mineRow--;
            }
        }
        new main().calculateNearbyMine();
    }

    public void ripplemines(int row, int col) {
        if (booms[row][col].hasMine || booms[row][col].flaged) {
            return;
        }
    }

    public void  calculateNearbyMine() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int x = booms[i][j].getX() - 1; x <= booms[i][j].getX() + 1; x++) {
                    for (int y = booms[i][j].getY() - 1; y <= booms[i][j].getY() + 1; y++) {
                        if(x >= 0 && x < rows && y >= 0 && y < cols) {
                            booms[i][j].neighbour.add(booms[x][y]);
                        }
                    }
                }
            }
        }

    }


}

