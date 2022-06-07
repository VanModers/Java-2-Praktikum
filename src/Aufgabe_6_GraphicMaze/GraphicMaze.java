package Aufgabe_6_GraphicMaze;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphicMaze extends JFrame {

    private final Graphic graphic;
    private final Maze mazeSolver;

    private final char[][] maze = {{' ', 'X', ' ', 'X', ' ', ' '},
                              {' ', 'X', ' ', ' ', ' ', 'X'},
                              {' ', ' ', 'X', 'X', ' ', 'X'},
                              {'X', ' ', ' ', ' ', ' ', 'X'},
                              {' ', ' ', ' ', 'X', ' ', 'X'},
                              {'X', 'X', ' ', ' ', ' ', ' '}};
     /*private char[][] maze =
                    {{' ', 'X', ' ', ' ', ' ', 'X', ' '},
                    {' ', 'X', ' ', 'X', ' ', ' ', 'X'},
                    {' ', 'X', ' ', 'X', 'X', ' ', ' '},
                    {' ', 'X', ' ', 'X', ' ', ' ', 'X'},
                    {' ', ' ', ' ', 'X', ' ', 'X', ' '},
                    {'X', ' ', 'X', 'X', ' ', ' ', 'X'},
                    {'X', ' ', 'X', ' ', ' ', ' ', ' '}};*/

    private final int mazeWidth = maze[0].length;
    private final int mazeHeight = maze.length;

    public GraphicMaze() {
        super();

        mazeSolver = new Maze(maze);
        mazeSolver.canExit(0, 0);

        Container c = this.getContentPane();
        c.setLayout(new FlowLayout());
        graphic = new Graphic(400, 400);
        c.add(graphic);
        drawMaze();
    }

    private void drawMaze() {
        for (int x = 0; x < maze[0].length; x++) {
            for (int y = 0; y < maze.length; y++) {
                graphic.setColor(Color.black);
                graphic.drawRect(graphic.width / mazeWidth * x, graphic.height / mazeHeight * y, graphic.width / mazeWidth, graphic.height / mazeHeight);
                if (maze[y][x] == 'X')
                    graphic.fillRect(graphic.width / mazeWidth * x, graphic.height / mazeHeight * y, graphic.width / mazeWidth, graphic.height / mazeHeight);
            }
        }
    }

    private void drawPath() {
        List<Point> mazeSolution = mazeSolver.getSolution();
        int size = (int) (graphic.height / mazeHeight * 0.85);
        int offset = (graphic.height / mazeHeight - size) / 2;
        for (Point p : mazeSolution) {
            graphic.setColor(Color.blue);
            graphic.fillOval(graphic.height / mazeHeight * p.y + offset, graphic.width / mazeWidth * p.x + offset, size, size);
        }
    }

    public static void main(String[] args) {
        GraphicMaze frame = new GraphicMaze();

        frame.setTitle("GraphicMaze");
        frame.pack();
        //frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.drawPath();
    }
}
