/*
 * CSCI 446 A.I.
 * Completed by: Robert Tiller, Kyle Ungersma, Jason Armstrong, Beau Anderson
 */

import java.io.File;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception 
    {
        
        Maze maze = Maze.createFromInput(new Scanner(new File("7x7maze.txt")), 7, 7);
        Basic run = new Basic(maze);
        System.out.println(maze + "\n");
        System.out.println(run.solveMaze());
        
        /*
        Maze maze = Maze.createFromInput(new Scanner(new File("12x12maze.txt")), 12, 12);
        Advanced run = new Advanced(maze);
        System.out.println(maze + "\n");
        System.out.println(run.solveMaze());
        */
    }
}
