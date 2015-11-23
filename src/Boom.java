import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by andrewliu on 15-11-22.
 */
public class Boom extends JButton {

    boolean checked = false;
    boolean flaged = false;
    boolean hasMine = false;

    int currentRow;
    int currentCol;

    ArrayList<Boom> neighbour;

    int nearbyMines;

    public Boom(int row, int col) {
        currentCol = col;
        currentRow = row;

        neighbour = new ArrayList<Boom>();
        nearbyMines = 0;

    }


    public Boom(String img) {
        this.getIcon(img);
        this.addMouseListener(new myMouseListener());
    }

    private static ImageIcon getIcon(String iconName) {
        String relative_url = "/home/andrewliu/IdeaProjects/MineWepper/Image/";
        URL url = Boom.class.getResource(relative_url + iconName);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.out.println("加载icon失败:" + relative_url + iconName);
        }
        return null;
    }


    class BoomListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

        }
    }


    //这里就体现出内部类的优越性，可以使用外部类的参数
    class myMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int c = e.getButton();
            if (c == e.BUTTON1) {
                //right click
                if(!checked) {
                    if (flaged) {

                    } else {

                    }
                }


                System.out.println("right click");
            } else if (c == e.BUTTON3) {
                if (checked || flaged) {

                } else if (hasMine) {

                }

                System.out.println("left click");
            }

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
    }

    public boolean getChecked() {
        return checked;
    }

    public boolean getFlaged() {
        return flaged;
    }

    public void setChecked(boolean b) {
        this.checked = b;
    }

    public void setFlaged(boolean b) {
        this.flaged = b;
    }

    public void setMine(boolean b) {
        this.hasMine = b;
    }

    public boolean hasMine() {
        return this.hasMine;
    }

    public int getRow() {
        return currentRow;
    }

    public int getCol() {
        return currentCol;
    }

    public int calculateNearbyBooms(Boom b) {
        this.neighbour.add(b);
        if(b.hasMine)
            this.nearbyMines++;
        return nearbyMines;
    }

}
