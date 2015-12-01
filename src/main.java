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
    JLabel statusBar;
    Boom booms[][];

    int numOfMines = 10;
    int rows = 9;
    int cols = 9;


    public static void main(String[] args) {

        Layout layout = new Layout();

        layout.buildGUI();

    }






}

