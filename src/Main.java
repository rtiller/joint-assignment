/*
 * CSCI 446 A.I.
 * Completed by: Robert Tiller, Kyle Ungersma, Jason Armstrong, Beau Anderson
 */

import java.io.File;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception 
    {
    
        Maze maze = Maze.createMaze(new Scanner(new File("7x7maze.txt")), 7, 7);
        Dumb run = new Dumb(maze);
        maze.printMaze();
        System.out.println(run.solveMaze());
        
        /*
        Maze maze = Maze.createFromInput(new Scanner(new File("7x7maze.txt")), 7, 7);
        Smart run = new Smart(maze);
        maze.printMaze();
        System.out.println(run.solveMaze());
        */
    }
}
