import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by Andrew on 11/30/2015.
 */
public class Layout implements MouseListener {

    boolean isGameOver = false;
    boolean areMineSet = false;
    boolean isFirstClick = true;

    JPanel mainPanel;
    JPanel status;
    //ArrayList<Boom> booms;
    JFrame theFrame;
    JLabel statusBar;
    JLabel time;
    Boom booms[][];

    Timer timer;
    Timer Counter;


    int numOfMines = 10;
    int rows = 9;
    int cols = 9;
    int boomsRemaining = numOfMines;

    int randx = 0;
    int randy = 0;

    public Layout() {
    }

    public void buildGUI() {

        theFrame = new JFrame("MineWepper");
        theFrame.setTitle("MineSweeper");

        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        theFrame.add(statusBar, BorderLayout.SOUTH);
        booms = new Boom[9][9];

        GridLayout grid = new GridLayout(9, 9);
        mainPanel = new JPanel(grid);

        statusBar = new JLabel("");
        statusBar.setText("Ready to Go!");

        time = new JLabel();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:ss");
        time.setText(sdf.format(cal.getTime()));
        time.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.cancel();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        status = new JPanel(new BorderLayout());
        status.add(statusBar, "West");
        status.add(time, "East");

        timer = new Timer();
        timer.schedule(new RemindTask(), 0, 1000);

        Counter = new Timer();


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Boom b = new Boom("1.jpg");
                b.addMouseListener(this);
                b.setBackground(Color.gray);
                b.setForeground(Color.white);
                booms[i][j] = b;
                mainPanel.add(b);
            }
        }

        int count = 0;
        while (count < numOfMines) {
            randx = new Random().nextInt(rows);
            randy = new Random().nextInt(cols);
            if (booms[randx][randy].hasMine == false) {
                booms[randx][randy].hasMine = true;
                booms[randx][randy].checkwin = true;
                count++;
            }
        }
        theFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        theFrame.getContentPane().add(status, BorderLayout.SOUTH);

        theFrame.setBounds(100, 100, 500, 500);
        theFrame.setVisible(true);

//        RestartGame();
    }

    public String getTime() {
        String current_time;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        current_time = sdf.format(cal.getTime());
        return current_time;
    }

    public class CountTime extends TimerTask {
        int s = 0;
        int m = 0;
        int h = 0;
        public void run() {
            String second = Integer.toString(s);
            String minute = Integer.toString(m);
            String hour = Integer.toString(h);
            time.setText("Time: " + hour + ":" + minute + ":" + second);
            s++;
            if (s == 60) {
                m++;
                s = 0;
                if(m == 60) {
                    h++;
                    m = 0;
                }
            }
        }
    }

    public class RemindTask extends TimerTask {
        public void run() {
            time.setText(getTime());
        }
    }

    public void RestartGame(int sizex, int sizey, int row, int col, int numofmines) {

    }

    public void gameover() {
        Counter.cancel();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (booms[i][j].hasMine) {
                    booms[i][j].setLabel("*");
                    booms[i][j].setBackground(Color.red);
                }
                booms[i][j].setEnabled(false);
            }
        }

        int x2 = (int) cols / 2;
        int y2 = (int) rows / 2;
        booms [y2] [x2 - 4].setLabel("Y");
        booms [y2] [x2 - 3].setLabel ("O");
        booms [y2] [x2 - 2].setLabel("U");
        booms [y2] [x2 - 1].setLabel("");
        booms [y2] [x2].setLabel("L");
        booms [y2] [x2 + 1].setLabel("O");
        booms [y2] [x2 + 2].setLabel("S");
        booms [y2] [x2 + 3].setLabel("E");
        booms [y2] [x2 + 4].setLabel("!");
        for (int i = -4 ; i < 5 ; i++)
        {
            booms [y2] [x2 + i].setBackground(Color.black);
            booms [y2] [x2 + i].setForeground(Color.white);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (e.getSource() == booms[i][j]) {
                    if (isFirstClick) {
                        timer.cancel();
                        //timer.schedule(new CountTime(), 0, 1000);
                        Counter.schedule(new CountTime(), 0, 1000);
                    }
                    isFirstClick = false;

                    if (e.getButton() == e.BUTTON1 && booms[i][j].flaged == false) {
                        if (booms[i][j].hasMine) {
                            booms[i][j].setLabel("*");
                            gameover();
                            isGameOver = true;
                            break;
                        }
                        booms[i][j].checked = true;
                        booms[i][j].checkwin = true;
                        int numofnearby = calculateNearByMines(i, j);
                        String nearby = Integer.toString(numofnearby);
                        booms[i][j].setLabel(nearby);
                        if (numofnearby == 0) {
                            check(i, j);
                        }

                    } else if (e.getButton() == e.BUTTON3) {
                        if (booms[i][j].flaged) {
                            booms[i][j].setFlaged(false);
                            booms[i][j].checkwin = false;
                            booms[i][j].setForeground(Color.white);
                            booms[i][j].setLabel("");
                            boomsRemaining++;
                        } else {
                            booms[i][j].setFlaged(true);
                            booms[i][j].checkwin = true;
                            booms[i][j].setForeground(Color.red);
                            booms[i][j].setLabel("|>");
                            boomsRemaining--;
                        }
                    } else if (e.getButton() == e.BUTTON2 && booms[i][j].flaged == false && booms[i][j].checkwin == true && booms[i][j].hasMine == false) {
                        if (calculateNearbyFlags(i, j) == calculateNearByMines(i, j)) {
                            for (int rowx = i - 1; rowx <= i + 1; rowx++) {
                                for (int coly = j - 1; coly <= j + 1; coly++) {

                                       /* if (booms[rowx][coly].hasMine == false && booms[rowx][coly].flaged == true) {
                                            isGameOver = true;
                                            gameover();
                                            break;
                                        }*/

                                    if (rowx < 0 || coly < 0 || rowx >= rows || coly >= cols) // makes sure that it wont have an error for buttons next to the wall
                                        break;
                                    if (booms[rowx][coly].hasMine == false && booms[rowx][coly].flaged == true) //if there is no bomb, but there is a flag its game over
                                    {
                                        gameover();
                                        isGameOver = true;
                                        break;
                                    }
                                }

                            }
                            if (isGameOver == false) {
                                // ckeck out
                                check(i, j);
                            }
                        } else
                            break;
                    }
                    if (isGameOver == false) {
                        clicked();
                    }
                }
            }
        }
        checkwin();
    }

    public void clicked ()     //changes the color of the buttons and if [x][y] is "0" set the label to nothing
    {
        for (int x = 0 ; x < rows ; x++)
        {
            for (int y = 0 ; y < cols ; y++)
            {
                if (booms [x] [y].checkwin == true && booms [x] [y].flaged == false && booms [x] [y].hasMine == false)
                    booms [x] [y].setBackground (Color.darkGray);
                if (booms [x] [y].flaged == false && calculateNearByMines (x, y) == 0)
                    booms [x] [y].setLabel ("");
            }
        }
    }

    //TODO check the difference between the two logical expressions

   /* public void expose(int x, int y) {
        String surrBooms;
        booms[x][y].checked = true;
        for (int row = x - 1; row <= x + 1; row++) {
            for (int col = y - 1; col <= y + 1; col++) {
                if (row >= 0 && row < rows && col >= 0 && col < cols) {
                    if (booms[row][col].flaged != true) {
                        *//*if (calculateNearByMines(row, col) == 0) {
                            booms[row][col].setLabel("");
                        }*//*
                        booms[row][col].checkwin = true;
                        surrBooms = Integer.toString(calculateNearByMines(row, col));
                        booms[row][col].setLabel(surrBooms);
                        *//*booms[row][col].checked = true;*//*
                        //booms[row][col].setBackground(Color.darkGray);
                        break;
                    }
                }
            }
        }
    }

    public void surr(int x, int y) {
        for (int row = x - 1; row <= x + 1; row++) {
            for (int col = y - 1; col <= y + 1; col++) {
                *//*if (row >= 0 && row < rows && col >= 0 && col < cols) {
                    if (booms[row][col].flaged != true) {
                        if ( calculateNearByMines(row, col) == 0) {
                            check(row, col);
                        }
                    }
                    break;
                }*//*
                while (true)
                {
                    if (row < 0 || col < 0 || row >= rows || col >= cols) // makes sure that it wont have an error for buttons next to the wall
                        break;
                    if (booms [row] [col].flaged == true)
                        break;
                    if (booms [row] [col].checked == false && calculateNearByMines (row, col) == 0)
                    {
                        //expose (q, w);
                        check (row, col);
                    }
                    break;
                }
            }
        }
    }*/

    public void expose (int x, int y)  // exposes the surrounding 8 buttons
    {
        String surrbombs;
        booms [x] [y].checked = true;
        for (int q = x - 1 ; q <= x + 1 ; q++)
        {
            for (int w = y - 1 ; w <= y + 1 ; w++)
            {
                while (true)
                {
                    if (q < 0 || w < 0 || q >= rows || w >= cols) // makes sure that it wont have an error for buttons next to the wall
                        break;
                    if (booms[q] [w].flaged == true)
                        break;

                    booms [q] [w].checkwin = true;
                    surrbombs = Integer.toString (calculateNearByMines(q, w));
                    booms [q] [w].setLabel (surrbombs);
                    break;

                }
            }
        }
    }


    public void surr (int x, int y)  //this is what checks if a surrounding button has "0" is so expose it and check around the exposed buttons until there is no more "0"'s
    {
        String surrbombs;
        for (int q = x - 1 ; q <= x + 1 ; q++)
        {
            for (int w = y - 1 ; w <= y + 1 ; w++)
            {
                while (true)
                {
                    if (q < 0 || w < 0 || q >= rows || w >= cols) // makes sure that it wont have an error for buttons next to the wall
                        break;
                    if (booms [q] [w].flaged == true)
                        break;
                    if (booms [q] [w].checked == false && calculateNearByMines (q, w) == 0)
                    {
                        //expose (q, w);
                        check (q, w);
                    }
                    break;
                }
            }
        }
    }


    public void check(int x, int y) {
        expose(x, y);
        surr(x, y);
    }

    public void checkwin() {
        boolean allchecked = false;
        int numofFlags = calculateFlag();
        if (numofFlags == numOfMines) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {

                    if (booms[i][j].flaged == true && booms[i][j].hasMine == true) {
                        allchecked = true;
                    }
                }
            }
        }

        if (allchecked == true) {
            int x2 = (int) cols / 2;
            int y2 = (int) rows / 2;
            booms [y2] [x2 - 4].setLabel("Y");
            booms [y2] [x2 - 3].setLabel ("O");
            booms [y2] [x2 - 2].setLabel("U");
            booms [y2] [x2 - 1].setLabel("");
            booms [y2] [x2].setLabel("W");
            booms [y2] [x2 + 1].setLabel("I");
            booms [y2] [x2 + 2].setLabel("N");
            booms [y2] [x2 + 3].setLabel("!");
            booms [y2] [x2 + 4].setLabel("!");
            for (int i = -4 ; i < 5 ; i++)
            {
                booms [y2] [x2 + i].setBackground(Color.black);
                booms [y2] [x2 + i].setForeground(Color.white);
            }
        }

    }

    public int calculateFlag() {
        int flag_num = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (booms[i][j].flaged == true) {
                    flag_num++;
                }
            }
        }
        return flag_num;
    }



    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int calculateNearByMines(int x, int y) {
        int nearbyMines = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                while (true) {
                    if(i < 0 || j < 0 || i >= rows || j >= cols)
                        break;

                    if (booms[i][j].hasMine) {
                        nearbyMines++;
                    }
                   break;
                }
            }
        }
        return nearbyMines;
    }

    public int calculateNearbyFlags(int x, int y) {
        int nearbyFlags = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                while (true) {
                    /*if (i >= 0 && i < rows && j >= 0 && j < cols) {
                        if (booms[i][j].hasMine) {
                            nearbyFlags++;
                        }
                        break;
                    }*/
                    if (i < 0 || j < 0 || i >= rows || j >= cols) // makes sure that it wont have an error for buttons next to the wall
                        break;
                    if (booms [i] [j].flaged == true)
                        nearbyFlags++;
                    break;
                }
            }
        }
        return nearbyFlags;
    }


}
