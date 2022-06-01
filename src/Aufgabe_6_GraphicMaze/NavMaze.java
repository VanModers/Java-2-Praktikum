package Aufgabe_6_GraphicMaze;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class NavMaze extends JFrame {

    private final Graphic graphic;

    private final Maze mazeSolver;

    private int pathID = 0;

    private final char[][] maze = {{' ', 'X', ' ', 'X', ' ', ' '},
            {' ', 'X', ' ', ' ', ' ', 'X'},
            {' ', ' ', 'X', 'X', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', 'X'},
            {' ', ' ', ' ', 'X', ' ', 'X'},
            {'X', 'X', ' ', ' ', ' ', ' '}};

    public NavMaze() {
        super();

        mazeSolver = new Maze(maze);
        mazeSolver.canExit(0, 0);

        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        graphic = new Graphic(400, 400);
        c.add(graphic, BorderLayout.CENTER);

        Container south = new Container();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(50);
        south.setLayout(flowLayout);

        JButton zurueck = new JButton();
        zurueck.setText("Zurück");
        zurueck.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pathID--;
                pathID = Math.max(pathID, 0);
                drawPathPoint(pathID);
            }
        });
        south.add(zurueck);

        JButton vor = new JButton();
        vor.setText("Vor");
        vor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pathID++;
                pathID = Math.min(pathID, mazeSolver.getSolution().size() - 1);
                drawPathPoint(pathID);
            }
        });
        south.add(vor);

        c.add(south, BorderLayout.SOUTH);
    }

    private void drawMaze() {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                graphic.setColor(Color.black);
                graphic.drawRect(graphic.width / 6 * x, graphic.height / 6 * y, graphic.width / 6, graphic.width / 6);
                if (maze[y][x] == 'X')
                    graphic.fillRect(graphic.width / 6 * x, graphic.height / 6 * y, graphic.width / 6, graphic.width / 6);
            }
        }
    }

    private void drawPathPoint(int i) {
        graphic.clear();
        drawMaze();

        List<Point> mazeSolution = mazeSolver.getSolution();
        int size = (int) (graphic.height / 6 * 0.85);
        int offset = (graphic.height / 6 - size) / 2;
        Point p = mazeSolution.get(i);
        graphic.setColor(Color.blue);
        graphic.fillOval(graphic.height / 6 * p.y + offset, graphic.width / 6 * p.x + offset, size, size);

        graphic.redraw();
    }

    public static void main(String[] args) {
        NavMaze frame = new NavMaze();

        frame.setTitle("GraphicMaze");
        frame.pack();
        //frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.drawPathPoint(0);
    }
}
