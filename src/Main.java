
import java.io.File;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception 
    {
        Maze maze = Maze.createFromInput(new Scanner(new File("7x7maze.txt")), 7, 7);
        FreeFlowCSP run = new FreeFlowCSP(maze);
        System.out.println(maze + "\n");
        System.out.println(run.solveMaze());
    }
}
