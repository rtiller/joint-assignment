/*
 * CSCI 446 A.I.
 * Completed by: Robert Tiller, Kyle Ungersma, Jason Armstrong, Beau Anderson
 */

import java.util.ArrayList;
import java.util.Scanner;


public class Maze {
    int width;
    int height;
    Node[][]maze;
    
        ArrayList<Character> domain;
    
    
    
    
    
    private Maze(Node[][] mazeIn, ArrayList<Character> domain)
    {
        
        this.width = mazeIn[0].length;
        this.height = mazeIn.length;
        this.maze = mazeIn;
        this.domain = domain;
        
    }
    
    
    
    public static Maze createMaze(Scanner input, int width, int height)
    {
        Node[][] maze = new Node[height][width];
        ArrayList<Character> domain = new ArrayList<>();
        
        for (int y = 0; y < height; y++)
        {
            // Extract line of input as array of characters (values/non-value).
            char[] line = input.nextLine().toCharArray();
            
            // Assign objects to grid based on each value/non-value.
            for (int x = 0; x < width; x++)
            {
                char cell = line[x];
                
                // If the cell's symbol refers to empty...
                if (cell == Node.EmptyCell)
                {
                    // Create empty cell.
                    maze[y][x] = Node.createEmptyCell(x, y);
                    // Otherwise...
                }
                else
                {
                    // Create source cell and add value to domain if not already included.
                    maze[y][x] = Node.createSourceCell(x, y, cell);
                    if (!domain.contains(cell)) domain.add(cell);
                }
            }
        }
        
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                Node cell = maze[y][x];
                // Add neighbors of cell, if exist...
                if (x - 1 >= 0) cell.addNeighbor(maze[y][x-1]);
                if (x + 1 <= width - 1) cell.addNeighbor(maze[y][x+1]);
                if (y - 1 >= 0) cell.addNeighbor(maze[y-1][x]);
                if (y + 1 <= height - 1) cell.addNeighbor(maze[y+1][x]);
            }
        }
        
        // Now update domains
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                Node cell = maze[y][x];
                if (!cell.visited) cell.updateDomain(domain);
            }
        }
        return new Maze(maze, domain);
    }
    
    
    public Node getNode(int x, int y) { return this.maze[y][x]; }
    
    
    public void printMaze()
    {
        for (int i = 0; i < maze.length; i++)
        {
            Node[] row = maze[i];
            for (int j = 0; j < row.length; j++)
            {
                Node node = row[j];
                System.out.print(node.color);
            }
            System.out.println();
        }
    }
    
    
    public boolean isFilled()
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (getNode(x, y).visited == false)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    
}
