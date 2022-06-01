package Aufgabe_6_GraphicMaze;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Maze {

	final private char[][] maze;

	final private List<Point> solution;

	public Maze(char[][] maze) {
		this.maze = maze;
		solution = new ArrayList<>();
	}

	public boolean canExit(int i, int j) {
		
		int n = maze.length;
		
		if (i < 0 || j < 0 || i >= n || j >= n) // outside
			return false;
		
		if (maze[i][j] != ' ') // Mauer
			return false;
		
		maze[i][j] = '.';
		
		if ((i == n-1 && j == n-1) // Ziel erreicht
				|| canExit(i-1, j) || canExit(i, j+1)
				|| canExit(i+1, j) || canExit(i, j-1)) {
			System.out.println("("+j+", "+i+")");
			maze[i][j] = '+';
			solution.add(0, new Point(i, j));
			return true;
		}

		return false;
	}

	public List<Point> getSolution() {
		return solution;
	}

	public void printMaze() {
        for (char[] chars : maze) {
            for (int j = 0; j < maze.length; j++)
                System.out.print(chars[j] + " ");
            System.out.println();
        }
	}
}
